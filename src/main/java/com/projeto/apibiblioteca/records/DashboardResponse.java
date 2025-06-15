package com.projeto.apibiblioteca.records;

import java.util.List;

public record DashboardResponse(
        long totalUsers,
        long totalBooks,
        long totalOrders,
        List<DateQuantity> dailyStats
) {
    public record DateQuantity(String date, int quantity) {}
}
