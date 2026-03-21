package io.raineri.statistics.service.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class TransactionRequest {
    @JsonProperty("valor")
    private Double value;
    @JsonProperty("dataHora")
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime date;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }
}
