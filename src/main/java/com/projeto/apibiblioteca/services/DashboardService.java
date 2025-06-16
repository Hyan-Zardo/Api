package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.records.DashboardResponse;
import com.projeto.apibiblioteca.repositories.BookRepository;
import com.projeto.apibiblioteca.repositories.DashRepository;
import com.projeto.apibiblioteca.repositories.PurchaseOrderRepository;
import com.projeto.apibiblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PurchaseOrderRepository orderRepository;

    @Autowired
    private DashRepository dashRepository;

    public DashboardResponse getDashboardData(Instant startUtc, Instant endUtc) {

        if (startUtc.isAfter(endUtc)) {
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        }


        List<DashboardResponse.DateQuantity> dailyStats = dashRepository.findDailyStatsBetweenDates(startUtc, endUtc);


        List<DashboardResponse.DateQuantity> convertedStats = dailyStats.stream()
                .map(stat -> {

                    String brazilDate = Instant.parse(stat.date() + "T00:00:00Z")
                            .atZone(ZoneId.of("America/Sao_Paulo"))
                            .toLocalDate()
                            .toString();
                    return new DashboardResponse.DateQuantity(brazilDate, stat.quantity());
                })
                .toList();

        return new DashboardResponse(
                userRepository.count(),
                bookRepository.count(),
                orderRepository.count(),
                convertedStats
        );
    }
}