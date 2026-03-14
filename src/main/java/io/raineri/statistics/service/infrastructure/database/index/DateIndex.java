package io.raineri.statistics.service.infrastructure.database.index;

import java.util.*;

public class DateIndex {
    private TreeMap<Integer, HashSet<Integer>> data = new TreeMap<>();

    public void addIndex(Integer index, Integer value) {
        if(data.containsKey(index)) {
            data.get(index).add(value);
        }
        data.put(index, new HashSet<>(Set.of(value)));
    }

    public NavigableMap getByRange(int startDate, int endDate) {
        return data.subMap(startDate, true, endDate, true);
    }

    public HashSet get(Integer index) {
        return data.get(index);
    }

    public void cleanIndex() {
        data.clear();
    }

    public int sizeIndex() {
        return data.size();
    }
}
