package io.raineri.statistics.service.domain.exception.enums;

import io.raineri.statistics.service.domain.exception.SubcodeException;

public enum TransactionSubcode implements SubcodeException {
    NULL_DATE("Transaction date must not be null."),
    FUTURE_DATE("Transaction date cannot be in the future."),
    PRESENT_DATE("Transaction date cannot be the current moment."),
    NEGATIVE_VALUE("Transaction value must be greater than or equal to zero.");

    private final String message;

    TransactionSubcode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
