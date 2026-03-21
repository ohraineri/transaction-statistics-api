package io.raineri.statistics.service.application.gateway;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public interface StatisticsGateway {
    ArrayList<Integer> getValuesByDateRange(OffsetDateTime startDate, OffsetDateTime endDate);
}
