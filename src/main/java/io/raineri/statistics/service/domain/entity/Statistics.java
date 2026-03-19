package io.raineri.statistics.service.domain.entity;

import java.util.ArrayList;
import java.util.Collections;

public class Statistics {
    private int count;
    private int sum;
    private int average;
    private int minValue;
    private int maxValue;

    public Statistics(ArrayList<Integer> data) {
        this.count = data.toArray().length;
        this.sum = sumItems(data);
        this.average = (this.count == 0) ? 0 : this.sum / this.count;
        this.minValue = searchMinValue(data);
        this.maxValue = searchMaxValue(data);
    }

    public int getCount() {
        return count;
    }

    public int getSum() {
        return sum;
    }

    public int getAverage() {
        return average;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int searchMaxValue(ArrayList<Integer> data) {
        return Collections.max(data);
    }

    public int searchMinValue(ArrayList<Integer> data) {
        return Collections.min(data);
    }

    public int sumItems(ArrayList<Integer> data) {
        return data.stream().mapToInt(Integer::intValue).sum();
    }
}
