package io.raineri.statistics.service.infrastructure.gateway;

import io.raineri.statistics.service.domain.entity.Transaction;
import io.raineri.statistics.service.infrastructure.database.Database;

public class TransactionGatewayImpl implements io.raineri.statistics.service.application.gateway.TransactionGateway {

    @Override
    public void addTransaction(Transaction transaction) {
        Database.add(transaction);
    }

    @Override
    public void deleteAllTransaction() {
        Database.clean();
    }
}
