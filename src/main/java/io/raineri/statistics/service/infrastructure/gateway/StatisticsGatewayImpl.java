package io.raineri.statistics.service.infrastructure.gateway;

import io.raineri.statistics.service.application.gateway.StatisticsGateway;
import io.raineri.statistics.service.infrastructure.database.Database;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class StatisticsGatewayImpl implements StatisticsGateway {

    @Override
    public ArrayList<Integer> getValuesByDateRange(OffsetDateTime startDate, OffsetDateTime endDate) {
        return Database.whereDate(startDate, endDate);
    }
}
