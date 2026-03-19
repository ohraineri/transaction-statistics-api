package io.raineri.statistics.service.application.usecase;

import io.raineri.statistics.service.application.gateway.TransactionGateway;

public class RemoveTransaction {
    final private TransactionGateway transactionGateway;

    public RemoveTransaction(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    public void execute() {
        this.transactionGateway.deleteAllTransaction();
    }
}
