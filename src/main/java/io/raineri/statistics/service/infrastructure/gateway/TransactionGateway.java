package io.raineri.statistics.service.infrastructure.gateway;

import io.raineri.statistics.service.domain.entity.Transaction;
import io.raineri.statistics.service.infrastructure.database.Database;

public class TransactionGateway {

    public Transaction addTransaction(Transaction transaction) {
        return Database.add(transaction);
    }

    public void deleteAllTransaction() {
        Database.clean();
    }
}
