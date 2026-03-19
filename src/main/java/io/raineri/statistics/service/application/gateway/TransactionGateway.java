package io.raineri.statistics.service.application.gateway;

import io.raineri.statistics.service.domain.entity.Transaction;

public interface TransactionGateway {
    public void addTransaction(Transaction transaction);
    public void deleteAllTransaction();
}
