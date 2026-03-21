package io.raineri.statistics.service.application.usecase;

import io.raineri.statistics.service.application.usecase.contract.RetrieveStatisticsImpl;
import io.raineri.statistics.service.domain.entity.Statistics;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public class RetrieveStatistics {
    final private long DEFAULT_SECONDS = 60;
    final private RetrieveStatisticsImpl retrieveStatistics;

    public RetrieveStatistics(RetrieveStatisticsImpl retrieveStatistics) {
        this.retrieveStatistics = retrieveStatistics;
    }

    public Statistics execute(int secondRange) {
        if(secondRange > 60) {
            ArrayList<Integer> data = this.retrieveStatistics.getDataByDate(OffsetDateTime.now().minusSeconds(secondRange), OffsetDateTime.now());
            return new Statistics(data);
        }

        ArrayList<Integer> data = this.retrieveStatistics.getDataByDate(OffsetDateTime.now().minusSeconds(DEFAULT_SECONDS), OffsetDateTime.now());
        return new Statistics(data);
    }

}
