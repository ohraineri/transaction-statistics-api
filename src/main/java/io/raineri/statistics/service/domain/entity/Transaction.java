package io.raineri.statistics.service.domain.entity;

import io.raineri.statistics.service.domain.exception.TransactionException;
import io.raineri.statistics.service.domain.exception.TransactionSubcode;

import java.time.OffsetDateTime;

public class Transaction {
    final private int value;
    final private OffsetDateTime date;

    public Transaction(int value, OffsetDateTime date) {
        checkDate(date);
        checkValue(value);
        this.value = value;
        this.date = date;
    }

    private void checkDate(OffsetDateTime date) {
        final OffsetDateTime PRESENT_DATE = OffsetDateTime.now();
        if (date == null) {
            throw new TransactionException(TransactionSubcode.NULL_DATE);
        }
        if(date.isAfter(PRESENT_DATE)) {
            throw new TransactionException(TransactionSubcode.FUTURE_DATE);
        }

        if(date.isEqual(PRESENT_DATE)) {
            throw new TransactionException(TransactionSubcode.PRESENT_DATE);
        }
    }

    private void checkValue(int value) {
        if(value < 0) {
            throw new TransactionException(TransactionSubcode.NEGATIVE_VALUE);
        }
    }


    public int getValue() {
        return value;
    }

    public OffsetDateTime getDate() {
        return date;
    }
}
