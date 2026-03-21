package io.raineri.statistics.service.infrastructure.gateway;

import io.raineri.statistics.service.infrastructure.database.Database;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class StatisticsGatewayImpl {
    public ArrayList<Integer> getDataByDate(OffsetDateTime startDate, OffsetDateTime endDate) {
        return Database.whereDate(startDate, endDate);
    }
}
