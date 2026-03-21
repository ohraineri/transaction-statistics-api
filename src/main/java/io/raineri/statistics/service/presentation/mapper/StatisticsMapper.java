package io.raineri.statistics.service.presentation.mapper;

import io.raineri.statistics.service.domain.entity.Statistics;
import io.raineri.statistics.service.presentation.dto.StatisticsResponse;

public class StatisticsMapper {
    public static StatisticsResponse toResponse(Statistics statistics) {
        return new StatisticsResponse(
                statistics.getCount(),
                statistics.getSum(),
                statistics.getAverage(),
                statistics.getMinValue(),
                statistics.getMaxValue()
        );
    }
}
