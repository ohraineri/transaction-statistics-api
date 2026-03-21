package io.raineri.statistics.service.presentation.mapper;

import io.raineri.statistics.service.domain.entity.Transaction;
import io.raineri.statistics.service.presentation.dto.TransactionRequest;

public class TransactionMapper {
    static public Transaction toEntity(TransactionRequest transactionRequest) {
        return new Transaction(transactionRequest.getValue().intValue(), transactionRequest.getDate());
    }
}
