package io.raineri.statistics.service.application.usecase.contract;

import io.raineri.statistics.service.domain.entity.Statistics;

public interface RetrieveStatisticsImpl {
    public Statistics execute(long secondsRange);
}
