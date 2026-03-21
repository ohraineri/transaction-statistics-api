package io.raineri.statistics.service.domain.exception;

import io.raineri.statistics.service.domain.exception.enums.DomainCode;
import io.raineri.statistics.service.domain.exception.enums.TransactionSubcode;

public class TransactionException extends DomainException {
    public TransactionException(TransactionSubcode subcode) {
        super(DomainCode.INVALID_TRANSACTION, subcode);
    }
}
