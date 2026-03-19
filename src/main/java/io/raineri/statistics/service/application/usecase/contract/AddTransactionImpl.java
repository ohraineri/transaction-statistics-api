package io.raineri.statistics.service.application.usecase.contract;

import io.raineri.statistics.service.domain.entity.Transaction;

public interface AddTransactionImpl {
    public void execute(Transaction transaction);
}
