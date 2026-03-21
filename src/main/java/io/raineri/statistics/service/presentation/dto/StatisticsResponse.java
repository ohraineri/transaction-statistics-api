package io.raineri.statistics.service.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatisticsResponse {
    @JsonProperty("count")
    private int count;

    @JsonProperty("sum")
    private int sum;

    @JsonProperty("avg")
    private int avg;

    @JsonProperty("min")
    private int min;

    @JsonProperty("max")
    private int max;

    public StatisticsResponse(int count, int sum, int avg, int min, int max) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.min = min;
        this.max = max;
    }

    public int getCount() {
        return count;
    }

    public int getSum() {
        return sum;
    }

    public int getAvg() {
        return avg;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
