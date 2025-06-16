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
    public List<DashboardResponse.DateQuantity> findDailyStatsBetweenDates(Instant startUtc, Instant endUtc) {
        String sql = """
            SELECT 
                to_char(date_trunc('day', o.order_date at time zone 'UTC'), 'YYYY-MM-DD') as date,
                COUNT(o.id) as quantity
            FROM orders o
            WHERE o.order_date BETWEEN :start AND :end
            GROUP BY date_trunc('day', o.order_date at time zone 'UTC')
            ORDER BY date_trunc('day', o.order_date at time zone 'UTC')
            """;

        List<Object[]> results = em.createNativeQuery(sql)
                .setParameter("start", startUtc)
                .setParameter("end", endUtc)
                .getResultList();

        return results.stream()
                .map(row -> new DashboardResponse.DateQuantity(
                        (String) row[0],
                        ((Number) row[1]).intValue()
                ))
                .toList();
    }
}