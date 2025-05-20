package com.projeto.apibiblioteca.records;

import com.projeto.apibiblioteca.enums.OrderStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID userId,
        List<UUID> bookIds,
        Instant orderDate,
        OrderStatus orderStatus,
        LocalDate withdrawDate,
        LocalDate returnDate,
        Double totalPrice
) {}
