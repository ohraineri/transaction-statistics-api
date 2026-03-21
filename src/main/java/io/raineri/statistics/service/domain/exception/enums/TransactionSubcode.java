package io.raineri.statistics.service.domain.exception.enums;

import io.raineri.statistics.service.domain.exception.SubcodeException;

public enum TransactionSubcode implements SubcodeException {
    NULL_DATE("No date was given"),
    FUTURE_DATE("The date shown is in the future."),
    PRESENT_DATE("Data presents this in the present tense.");

    private final String message;

    TransactionSubcode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
