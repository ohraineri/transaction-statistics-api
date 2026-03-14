package io.raineri.statistics.service.entity;

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
        if (date == null) {
            // Error Handler
        }
        if(date.isAfter(OffsetDateTime.now())) {
            // Error Handler
        }

        if(date.isEqual(OffsetDateTime.now())) {
            // Error Handler
        }
    }

    private void checkValue(int value) {
        if(value < 0) {
            // Error Handler
        }
    }
}
