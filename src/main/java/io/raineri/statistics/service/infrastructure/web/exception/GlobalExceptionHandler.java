package io.raineri.statistics.service.infrastructure.web.exception;

import io.raineri.statistics.service.application.exception.ErrorResponse;
import io.raineri.statistics.service.domain.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpStatusResolver statusResolver;

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomain(DomainException ex) {
        return ResponseEntity
                .status(statusResolver.resolve(ex.getSubcode()))
                .body(ErrorResponse.from(ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "INTERNAL_ERROR",
                        "UNEXPECTED",
                        "An unexpected error occurred.",
                        Instant.now()
                ));
    }
}