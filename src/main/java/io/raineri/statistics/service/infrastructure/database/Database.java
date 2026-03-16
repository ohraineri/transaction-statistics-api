package io.raineri.statistics.service.infrastructure.database;

import java.util.HashMap;
import io.raineri.statistics.service.entity.Transaction;
import io.raineri.statistics.service.infrastructure.database.index.DateIndex;



public class Database {
    static private long indexCounter = 0;
    final static private HashMap<Long, Transaction> table = new HashMap<>();

    static public void add(Transaction transaction) {
        table.put(retrieveConcreteIndex(), transaction);
        DateIndex.add(transaction.getDate().toEpochSecond(), indexCounter);
    }

    static public long retrieveConcreteIndex() {
        indexCounter += 1;
        return indexCounter;
    }

    static public void clean() {
        table.clear();
        DateIndex.clear();
    }
    public static HashMap<Long, Transaction> getTable() {
        return table;
    }
}
