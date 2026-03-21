package io.raineri.statistics.service.application.usecase;

import io.raineri.statistics.service.application.gateway.StatisticsGateway;
import io.raineri.statistics.service.application.usecase.contract.RetrieveStatisticsImpl;
import io.raineri.statistics.service.domain.entity.Statistics;

import java.time.OffsetDateTime;

public class RetrieveStatistics implements RetrieveStatisticsImpl {
    private static final long DEFAULT_SECONDS = 60;
    private final StatisticsGateway statisticsGateway;

    public RetrieveStatistics(StatisticsGateway statisticsGateway) {
        this.statisticsGateway = statisticsGateway;
    }

    @Override
    public Statistics execute(long secondsRange) {
        long range = (secondsRange > 0 && secondsRange <= DEFAULT_SECONDS) ? secondsRange : DEFAULT_SECONDS;

        OffsetDateTime now = OffsetDateTime.now();
        return new Statistics(
                this.statisticsGateway.getValuesByDateRange(now.minusSeconds(range), now)
        );
    }

}
