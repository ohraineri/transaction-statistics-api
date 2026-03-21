package io.raineri.statistics.service.presentation.mapper;

import io.raineri.statistics.service.domain.entity.Statistics;
import io.raineri.statistics.service.presentation.dto.StatisticResponse;

public class StatisticMapper {
    StatisticResponse toResponse(Statistics statistic) {
        StatisticResponse statisticResponse = new StatisticResponse();
        statisticResponse.setCount((double) statistic.getCount() / 100);
        statisticResponse.setAvg((double) statistic.getAverage() / 100);
        statisticResponse.setSum((double) statistic.getSum() / 100);
        statisticResponse.setMin((double) statistic.getMinValue() / 100);
        statisticResponse.setMax((double) statistic.getMaxValue() / 100);
        return statisticResponse;
    }
}
