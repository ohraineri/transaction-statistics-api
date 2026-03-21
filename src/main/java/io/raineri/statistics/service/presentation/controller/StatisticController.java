package io.raineri.statistics.service.presentation.controller;

import io.raineri.statistics.service.application.usecase.contract.RetrieveStatisticsImpl;
import io.raineri.statistics.service.presentation.mapper.StatisticsMapper;
import io.raineri.statistics.service.presentation.dto.StatisticsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StatisticController {
    private final RetrieveStatisticsImpl retrieveStatistics;

    public StatisticController(RetrieveStatisticsImpl retrieveStatistics) {
        this.retrieveStatistics = retrieveStatistics;
    }

    @GetMapping("/estatistica")
    public StatisticsResponse get(@RequestParam(required = false) Long secondsRange) {
        long range = (secondsRange == null) ? 0 : secondsRange;
        return StatisticsMapper.toResponse(this.retrieveStatistics.execute(range));
    }
}
