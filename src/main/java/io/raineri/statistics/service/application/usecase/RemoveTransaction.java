package io.raineri.statistics.service.application.usecase;

import io.raineri.statistics.service.application.gateway.TransactionGateway;
import io.raineri.statistics.service.application.usecase.contract.RemoveTransactionImpl;

public class RemoveTransaction implements RemoveTransactionImpl {
    final private TransactionGateway transactionGateway;

    public RemoveTransaction(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    public void execute() {
        this.transactionGateway.deleteAllTransaction();
    }
}
