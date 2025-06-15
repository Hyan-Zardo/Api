package com.projeto.apibiblioteca.repositories;

import com.projeto.apibiblioteca.records.DashboardResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

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

        return results.stream()
                .map(r -> new DashboardResponse.DateQuantity(
                        (String) r[0],
                        ((Number) r[1]).intValue()))
                .toList();
    }
}