package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.records.DashboardResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DashRepositoryImpl implements DashRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DashboardResponse.DateQuantity> findDailyStatsBetweenDates(Instant start, Instant end) {

        String sql = """
            SELECT 
                to_char(date_trunc('day', o.order_date), 'YYYY-MM-DD') as date,
                COUNT(o.id) as quantity
            FROM orders o
            WHERE o.order_date BETWEEN :start AND :end
            GROUP BY date_trunc('day', o.order_date)
            ORDER BY date_trunc('day', o.order_date)
            """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();


        Map<String, Integer> ordersByDate = results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).intValue()
                ));


        List<DashboardResponse.DateQuantity> allDays = new ArrayList<>();
        LocalDate currentDate = start.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.atZone(ZoneId.systemDefault()).toLocalDate();

        while (!currentDate.isAfter(endDate)) {
            String dateKey = currentDate.format(DateTimeFormatter.ISO_DATE);
            int quantity = ordersByDate.getOrDefault(dateKey, 0);

            allDays.add(new DashboardResponse.DateQuantity(dateKey, quantity));
            currentDate = currentDate.plusDays(1);
        }

        return allDays;
    }
}
