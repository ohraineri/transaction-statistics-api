package io.raineri.statistics.service.application.exception;

import io.raineri.statistics.service.domain.exception.DomainException;

import java.time.Instant;

public record ErrorResponse(
        String code,
        String subcode,
        String message,
        Instant timestamp
) {
    public static ErrorResponse from(DomainException ex) {
        return new ErrorResponse(
                ex.getCode().toString(),
                ex.getSubcode().toString(),
                ex.getSubcode().getMessage(),
                Instant.now()
        );
    }
}