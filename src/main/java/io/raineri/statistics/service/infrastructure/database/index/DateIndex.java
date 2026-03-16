package io.raineri.statistics.service.infrastructure.database.index;

import java.util.*;

public class DateIndex {
    static private TreeMap<Long, HashSet<Long>> entriesByDate = new TreeMap<>();

    static public void add(Long date, Long rowId) {
        if (entriesByDate.containsKey(date)) {
            entriesByDate.get(date).add(rowId);
        }
        entriesByDate.put(date, new HashSet<>(Set.of(rowId)));
    }

    static public NavigableMap<Long, HashSet<Long>> findByDateRange(long startDate, long endDate) {
        return entriesByDate.subMap(startDate, true, endDate, true);
    }

    static public HashSet<Long> findByDate(Long date) {
        return entriesByDate.get(date);
    }

    static public void clear() {
        entriesByDate.clear();
    }

    static public int size() {
        return entriesByDate.size();
    }
}