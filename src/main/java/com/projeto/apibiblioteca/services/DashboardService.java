package com.projeto.apibiblioteca.services;

import com.projeto.apibiblioteca.records.DashboardResponse;
import com.projeto.apibiblioteca.repositories.BookRepository;
import com.projeto.apibiblioteca.repositories.DashRepository;
import com.projeto.apibiblioteca.repositories.PurchaseOrderRepository;
import com.projeto.apibiblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Cacheable("dashboardData")
    public DashboardResponse getDashboardData(LocalDate startDate, LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Data inicial não pode ser após data final");
        }

        long totalUsers = userRepository.count();
        long totalBooks = bookRepository.count();
        long totalOrders = orderRepository.count();

        List<DashboardResponse.DateQuantity> dailyStats = dashRepository.findDailyStatsBetweenDates(
                startDate.atStartOfDay(ZoneId.systemDefault()).toInstant(),
                endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant()
        );

        return new DashboardResponse(totalUsers, totalBooks, totalOrders, dailyStats);
    }
}
