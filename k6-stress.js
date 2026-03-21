import http from "k6/http";
import { check, sleep } from "k6";
import { Rate, Trend, Counter } from "k6/metrics";

const errorRate = new Rate("error_rate");
const successRate = new Rate("success_rate");
const latency = new Trend("latency");
const transactionsOk = new Counter("transactions_ok");
const transactionsFail = new Counter("transactions_fail");

const BASE_URL = __ENV.BASE_URL || "http://localhost:8080";
const ENDPOINT_PATH = __ENV.ENDPOINT_PATH || "/transacao";
const TZ_OFFSET = __ENV.TZ_OFFSET || "-03:00";
const REQUEST_TIMEOUT = __ENV.REQUEST_TIMEOUT || "10s";

const MAX_PAST_SECONDS = clampInt(parseInt(__ENV.MAX_PAST_SECONDS || "3600", 10), 1, 365 * 24 * 60 * 60);
const MIN_SLEEP_MS = clampInt(parseInt(__ENV.MIN_SLEEP_MS || "50", 10), 0, 60_000);
const MAX_SLEEP_MS = clampInt(parseInt(__ENV.MAX_SLEEP_MS || "300", 10), MIN_SLEEP_MS, 60_000);

export const options = {
  stages: [
    { duration: "30s", target: 10  },
    { duration: "2m",  target: 100 },
    { duration: "3m",  target: 100 },
    { duration: "2m",  target: 300 },
    { duration: "3m",  target: 300 },
    { duration: "30s", target: 600 },
    { duration: "1m",  target: 100 },
    { duration: "5m",  target: 200 },
    { duration: "1m",  target: 0   },
  ],
  thresholds: {
    http_req_duration: ["p(95)<2000", "p(99)<5000"],
    latency:           ["p(99)<5000"],
    error_rate:        ["rate<0.05"],
    success_rate:      ["rate>0.95"],
    http_reqs:         ["count>1000"],
  },
};

const AMOUNT_PROFILES = [
  { min: 0.01, max: 10.0 },
  { min: 10.01, max: 500.0 },
  { min: 500.01, max: 5000.0 },
  { min: 5000.01, max: 50000.0 },
  { min: 0.01, max: 0.01 },
];

function randomIntBetween(min, max) {
  const minInt = Math.ceil(min);
  const maxInt = Math.floor(max);
  return Math.floor(Math.random() * (maxInt - minInt + 1)) + minInt;
}

function clampInt(value, min, max) {
  if (Number.isNaN(value)) return min;
  return Math.min(max, Math.max(min, value));
}

function parseTzOffsetMinutes(offset) {
  const m = /^([+-])(\d{2}):?(\d{2})$/.exec(offset);
  if (!m) return -180; // default -03:00
  const sign = m[1] === "-" ? -1 : 1;
  const hours = parseInt(m[2], 10);
  const minutes = parseInt(m[3], 10);
  return sign * (hours * 60 + minutes);
}

function formatIsoWithOffset(utcMs, offset) {
  const offsetMinutes = parseTzOffsetMinutes(offset);
  const localMs = utcMs + offsetMinutes * 60 * 1000;
  const d = new Date(localMs);

  const pad2 = (n) => String(n).padStart(2, "0");
  const pad3 = (n) => String(n).padStart(3, "0");

  return (
    `${d.getUTCFullYear()}-${pad2(d.getUTCMonth() + 1)}-${pad2(d.getUTCDate())}` +
    `T${pad2(d.getUTCHours())}:${pad2(d.getUTCMinutes())}:${pad2(d.getUTCSeconds())}.${pad3(d.getUTCMilliseconds())}` +
    `${offset}`
  );
}

function generateAmount() {
  const profile = AMOUNT_PROFILES[randomIntBetween(0, AMOUNT_PROFILES.length - 1)];
  const amount = Math.random() * (profile.max - profile.min) + profile.min;
  return Number(amount.toFixed(2));
}

function generatePastDateTime() {
  const secondsInPast = randomIntBetween(1, MAX_PAST_SECONDS);
  const utcMs = Date.now() - secondsInPast * 1000;
  return formatIsoWithOffset(utcMs, TZ_OFFSET);
}

function generatePayload() {
  return JSON.stringify({
    // Field names are part of the public API contract.
    valor: generateAmount(),
    dataHora: generatePastDateTime(),
  });
}

export default function () {
  const payload = generatePayload();

  const params = {
    headers: {
      "Content-Type": "application/json",
      "User-Agent": __ENV.USER_AGENT || "k6-stress-test/1.0",
      "X-Request-ID": `k6-${__VU}-${__ITER}-${Date.now()}`,
    },
    timeout: REQUEST_TIMEOUT,
  };

  const res = http.post(`${BASE_URL}${ENDPOINT_PATH}`, payload, params);

  latency.add(res.timings.duration);

  const ok = check(res, {
    "status is 2xx": (r) => r.status >= 200 && r.status < 300,
    "status is not 5xx": (r) => r.status < 500,
    "response is under 2s": (r) => r.timings.duration < 2000,
  });

  if (ok) {
    successRate.add(true);
    errorRate.add(false);
    transactionsOk.add(1);
  } else {
    successRate.add(false);
    errorRate.add(true);
    transactionsFail.add(1);

    console.error(
      JSON.stringify({
        vu: __VU,
        iter: __ITER,
        status: res.status,
        duration: res.timings.duration,
        body: (res.body || "").substring(0, 300),
        payload,
      })
    );
  }

  sleep(randomIntBetween(MIN_SLEEP_MS, MAX_SLEEP_MS) / 1000);
}

export function handleSummary(data) {
  const m = data.metrics;

  const formatNumber = (v, suffix = "") => (v !== undefined ? `${v.toFixed(2)}${suffix}` : "N/A");
  const formatPercent = (v) => (v !== undefined ? `${(v * 100).toFixed(2)}%` : "N/A");
  const metricValue = (metric, key) => metric?.values?.[key];

  const title = `STRESS TEST REPORT — ${ENDPOINT_PATH}`;
  const titleLine = title.padEnd(54).slice(0, 54);

  const summary = `
╔══════════════════════════════════════════════════════════════╗
║  ${titleLine}║
╠══════════════════════════════════════════════════════════════╣
║  Total requests         : ${String(metricValue(m.http_reqs, "count") ?? "N/A").padEnd(33)}║
║  Error rate             : ${formatPercent(metricValue(m.error_rate, "rate")).padEnd(33)}║
║  Success rate           : ${formatPercent(metricValue(m.success_rate, "rate")).padEnd(33)}║
╠══════════════════════════════════════════════════════════════╣
║  LATENCY (http_req_duration)                                 ║
║  ├ Average              : ${formatNumber(metricValue(m.http_req_duration, "avg"), "ms").padEnd(33)}║
║  ├ Median (p50)         : ${formatNumber(metricValue(m.http_req_duration, "p(50)"), "ms").padEnd(33)}║
║  ├ p90                  : ${formatNumber(metricValue(m.http_req_duration, "p(90)"), "ms").padEnd(33)}║
║  ├ p95                  : ${formatNumber(metricValue(m.http_req_duration, "p(95)"), "ms").padEnd(33)}║
║  ├ p99                  : ${formatNumber(metricValue(m.http_req_duration, "p(99)"), "ms").padEnd(33)}║
║  └ Max                  : ${formatNumber(metricValue(m.http_req_duration, "max"), "ms").padEnd(33)}║
╠══════════════════════════════════════════════════════════════╣
║  THROUGHPUT                                                  ║
║  ├ req/s (avg)          : ${formatNumber(metricValue(m.http_reqs, "rate"), " req/s").padEnd(33)}║
║  ├ Transactions OK      : ${String(metricValue(m.transactions_ok, "count") ?? "N/A").padEnd(33)}║
║  └ Transactions Failed  : ${String(metricValue(m.transactions_fail, "count") ?? "N/A").padEnd(33)}║
╠══════════════════════════════════════════════════════════════╣
║  CONNECTION                                                  ║
║  ├ Connect time p95     : ${formatNumber(metricValue(m.http_req_connecting, "p(95)"), "ms").padEnd(33)}║
║  └ Waiting time p95     : ${formatNumber(metricValue(m.http_req_waiting, "p(95)"), "ms").padEnd(33)}║
╚══════════════════════════════════════════════════════════════╝
`;

  console.log(summary);

  return {
    "stdout": summary,
    "results.json": JSON.stringify(data, null, 2),
  };
}
