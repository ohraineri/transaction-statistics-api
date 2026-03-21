package io.raineri.statistics.service.domain.exception;

import io.raineri.statistics.service.domain.exception.enums.DomainCode;

public class TransactionException extends RuntimeException {
    private CodeException code = DomainCode.INVALID_TRANSACTION;
    private SubcodeException subcode;

    public TransactionException(SubcodeException subcode) {
        this.subcode = subcode;
    }
}
