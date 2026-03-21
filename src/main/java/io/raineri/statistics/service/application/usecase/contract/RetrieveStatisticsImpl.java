package io.raineri.statistics.service.application.usecase.contract;

import java.time.OffsetDateTime;
import java.util.ArrayList;

public interface RetrieveStatisticsImpl {
    public ArrayList<Integer> getDataByDate(OffsetDateTime startDate, OffsetDateTime endDate);
}
