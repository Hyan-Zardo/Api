package com.projeto.apibiblioteca.records;

import com.projeto.apibiblioteca.enums.OrderType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OrderRequest(
        UUID userId,
        List<UUID> bookIds,
        OrderType type,
        LocalDate withdrawDate,
        LocalDate returnDate
) {}
