package io.raineri.statistics.service.infrastructure.database.index;

import java.util.*;

public class DateIndex {
    private TreeMap<Integer, HashSet<Integer>> entriesByDate = new TreeMap<>();

    public void add(Integer date, Integer rowId) {
        if (entriesByDate.containsKey(date)) {
            entriesByDate.get(date).add(rowId);
            return;
        }
        entriesByDate.put(date, new HashSet<>(Set.of(rowId)));
    }

    public NavigableMap<Integer, HashSet<Integer>> findByDateRange(int startDate, int endDate) {
        return entriesByDate.subMap(startDate, true, endDate, true);
    }

    public HashSet<Integer> findByDate(Integer date) {
        return entriesByDate.get(date);
    }

    public void clear() {
        entriesByDate.clear();
    }

    public int size() {
        return entriesByDate.size();
    }
}