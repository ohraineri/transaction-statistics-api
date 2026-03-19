package io.raineri.statistics.service.application.usecase;

import io.raineri.statistics.service.domain.entity.Transaction;
import io.raineri.statistics.service.application.gateway.TransactionGateway;
import io.raineri.statistics.service.application.usecase.contract.AddTransactionImpl;

public class AddTransaction implements AddTransactionImpl {

    final private TransactionGateway transactionGateway;

    public AddTransaction(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    public void execute(Transaction transaction) {
        transactionGateway.addTransaction(transaction);
    }
}
