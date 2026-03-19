package io.raineri.statistics.service.presentation.mapper;

import io.raineri.statistics.service.domain.entity.Transaction;
import io.raineri.statistics.service.presentation.dto.RequestTransaction;

public class TransactionMapper {
    static public Transaction toEntity(RequestTransaction requestTransaction) {
        return new Transaction(requestTransaction.getValue().intValue(), requestTransaction.getDate());
    }
}
