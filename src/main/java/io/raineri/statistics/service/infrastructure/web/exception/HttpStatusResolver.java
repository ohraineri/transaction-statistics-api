package io.raineri.statistics.service.infrastructure.web.exception;

import io.raineri.statistics.service.domain.exception.TransactionSubcode;
import io.raineri.statistics.service.domain.exception.SubcodeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HttpStatusResolver {

    public HttpStatus resolve(SubcodeException subcode) {
        return switch (subcode) {
            case TransactionSubcode ts -> switch (ts) {
                case PRESENT_DATE -> null;
                case NULL_DATE, NEGATIVE_VALUE -> HttpStatus.UNPROCESSABLE_ENTITY;
                case FUTURE_DATE               -> HttpStatus.UNPROCESSABLE_ENTITY;
            };
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}