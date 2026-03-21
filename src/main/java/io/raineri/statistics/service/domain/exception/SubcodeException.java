package io.raineri.statistics.service.domain.exception;

public sealed interface SubcodeException permits TransactionSubcode {
    public String getMessage();
}
