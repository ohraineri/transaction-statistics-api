package io.raineri.statistics.service.infrastructure.database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.time.OffsetDateTime;
import io.raineri.statistics.service.domain.entity.Transaction;
import io.raineri.statistics.service.infrastructure.database.index.DateIndex;



public class Database {
    static private long indexCounter = 0;
    final static private HashMap<Long, Transaction> table = new HashMap<>();

    static public Transaction add(Transaction transaction) {
        table.put(retrieveConcreteIndex(), transaction);
        DateIndex.add(transaction.getDate().toEpochSecond(), indexCounter);
        return transaction;
    }

    static public long retrieveConcreteIndex() {
        indexCounter += 1;
        return indexCounter;
    }

    static public void clean() {
        table.clear();
        DateIndex.clear();
    }

    static public ArrayList<Integer> whereDate(OffsetDateTime startDate, OffsetDateTime endDate){
        NavigableMap<Long, HashSet<Long>> result = DateIndex.findByDateRange(
                startDate.toEpochSecond(),
                endDate.toEpochSecond()
        );

        ArrayList<Integer> arrayList = new ArrayList<>();
        result.forEach((key, value) -> value.forEach(k -> arrayList.add(table.get(k).getValue())));
        return arrayList;
    }

    public static HashMap<Long, Transaction> getTable() {
        return table;
    }
}
