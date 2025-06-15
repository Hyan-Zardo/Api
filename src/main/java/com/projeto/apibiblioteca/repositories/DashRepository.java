package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.records.DashboardResponse;

import java.time.Instant;
import java.util.List;

public interface DashRepository {
    List<DashboardResponse.DateQuantity> findDailyStatsBetweenDates(Instant start, Instant end);
}
