package io.raineri.statistics.service.presentation.dto;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
public class RequestTransaction {
    @JsonProperty("valor")
    private Double value;
    @JsonProperty("dataHora")
    @DateTimeFormat(iso =  DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime date;
}
