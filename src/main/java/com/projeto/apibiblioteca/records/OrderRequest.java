package com.projeto.apibiblioteca.records;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record OrderRequest(
        UUID userId,
        List<UUID> bookIds,
        LocalDate withdrawDate,
        LocalDate returnDate,
        Integer quantity
) {}
