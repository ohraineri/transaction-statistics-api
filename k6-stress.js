import http from "k6/http";
import { check, sleep } from "k6";
import { Rate, Trend, Counter } from "k6/metrics";
import { randomIntBetween } from "https://jslib.k6.io/k6-utils/1.4.0/index.js";

const errorRate      = new Rate("error_rate");
const successRate    = new Rate("success_rate");
const latencyP99     = new Trend("latency_p99");
const transacoesOk   = new Counter("transacoes_ok");
const transacoesFail = new Counter("transacoes_fail");

const BASE_URL = __ENV.BASE_URL || "http://localhost:8080";

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
        latency_p99:       ["value<5000"],
        error_rate:        ["rate<0.05"],
        success_rate:      ["rate>0.95"],
        http_reqs:         ["count>1000"],
    },
};

const PERFIS = [
    { valorMin: 0.01,    valorMax: 10.00    },
    { valorMin: 10.01,   valorMax: 500.00   },
    { valorMin: 500.01,  valorMax: 5000.00  },
    { valorMin: 5000.01, valorMax: 50000.00 },
    { valorMin: 0.01,    valorMax: 0.01     },
];

function gerarValor() {
    const perfil = PERFIS[randomIntBetween(0, PERFIS.length - 1)];
    return parseFloat(
        (Math.random() * (perfil.valorMax - perfil.valorMin) + perfil.valorMin).toFixed(2)
    );
}

function gerarDataPassado() {
    const agora  = new Date();
    const offset = randomIntBetween(1, 3600);
    const utcMs  = agora.getTime() - offset * 1000 - 3 * 60 * 60 * 1000;
    const d      = new Date(utcMs);
    const pad    = (n) => String(n).padStart(2, "0");
    const ms     = String(d.getUTCMilliseconds()).padStart(3, "0");

    return (
        `${d.getUTCFullYear()}-${pad(d.getUTCMonth() + 1)}-${pad(d.getUTCDate())}` +
        `T${pad(d.getUTCHours())}:${pad(d.getUTCMinutes())}:${pad(d.getUTCSeconds())}.${ms}-03:00`
    );
}

function gerarPayload() {
    return JSON.stringify({
        valor:    gerarValor(),
        dataHora: gerarDataPassado(),
    });
}

export default function () {
    const payload = gerarPayload();

    const params = {
        headers: {
            "Content-Type": "application/json",
            "User-Agent":   "k6-stress-test/1.0",
            "X-Request-ID": `k6-${__VU}-${__ITER}-${Date.now()}`,
        },
        timeout: "10s",
    };

    const res = http.post(`${BASE_URL}/transacao`, payload, params);

    latencyP99.add(res.timings.duration);

    const ok = check(res, {
        "status é 2xx":            (r) => r.status >= 200 && r.status < 300,
        "status não é 5xx":        (r) => r.status < 500,
        "resposta em menos de 2s": (r) => r.timings.duration < 2000,
    });

    if (ok) {
        successRate.add(true);
        errorRate.add(false);
        transacoesOk.add(1);
    } else {
        successRate.add(false);
        errorRate.add(true);
        transacoesFail.add(1);

        console.error(
            JSON.stringify({
                vu:       __VU,
                iter:     __ITER,
                status:   res.status,
                duration: res.timings.duration,
                body:     res.body?.substring(0, 300),
                payload:  payload,
            })
        );
    }

    sleep(randomIntBetween(50, 300) / 1000);
}

export function handleSummary(data) {
    const m = data.metrics;

    const fmt = (v, unit = "ms") =>
        v !== undefined ? `${v.toFixed(2)}${unit}` : "N/A";

    const pct = (v) =>
        v !== undefined ? `${(v * 100).toFixed(2)}%` : "N/A";

    const summary = `
╔══════════════════════════════════════════════════════════════╗
║              RELATÓRIO DE STRESS TEST — /transacao           ║
╠══════════════════════════════════════════════════════════════╣
║  Total de requisições   : ${String(m.http_reqs?.values?.count ?? "N/A").padEnd(33)}║
║  Taxa de erro           : ${pct(m.error_rate?.values?.rate).padEnd(33)}║
║  Taxa de sucesso        : ${pct(m.success_rate?.values?.rate).padEnd(33)}║
╠══════════════════════════════════════════════════════════════╣
║  LATÊNCIA (http_req_duration)                                ║
║  ├ Média                : ${fmt(m.http_req_duration?.values?.avg).padEnd(33)}║
║  ├ Mediana (p50)        : ${fmt(m.http_req_duration?.values?.["p(50)"]).padEnd(33)}║
║  ├ p90                  : ${fmt(m.http_req_duration?.values?.["p(90)"]).padEnd(33)}║
║  ├ p95                  : ${fmt(m.http_req_duration?.values?.["p(95)"]).padEnd(33)}║
║  ├ p99                  : ${fmt(m.http_req_duration?.values?.["p(99)"]).padEnd(33)}║
║  └ Máximo               : ${fmt(m.http_req_duration?.values?.max).padEnd(33)}║
╠══════════════════════════════════════════════════════════════╣
║  THROUGHPUT                                                  ║
║  ├ req/s (média)        : ${fmt(m.http_reqs?.values?.rate, " req/s").padEnd(33)}║
║  ├ Transações OK        : ${String(m.transacoes_ok?.values?.count ?? "N/A").padEnd(33)}║
║  └ Transações Fail      : ${String(m.transacoes_fail?.values?.count ?? "N/A").padEnd(33)}║
╠══════════════════════════════════════════════════════════════╣
║  CONEXÃO                                                     ║
║  ├ Tempo de conexão p95 : ${fmt(m.http_req_connecting?.values?.["p(95)"]).padEnd(33)}║
║  └ Tempo de espera p95  : ${fmt(m.http_req_waiting?.values?.["p(95)"]).padEnd(33)}║
╚══════════════════════════════════════════════════════════════╝
`;

    console.log(summary);

    return {
        "stdout":       summary,
        "results.json": JSON.stringify(data, null, 2),
    };
}