package com.projeto.apibiblioteca.controllers;

import com.projeto.apibiblioteca.records.DashboardResponse;
import com.projeto.apibiblioteca.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private static final ZoneId BRAZIL_ZONE = ZoneId.of("America/Sao_Paulo");

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboardData(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate == null) {
            startDate = LocalDate.now(ZoneId.of("America/Sao_Paulo")).minusDays(7);
        }
        if (endDate == null) {
            endDate = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        }

        Instant startUtc = startDate.atStartOfDay(BRAZIL_ZONE).toInstant();
        Instant endUtc = endDate.atTime(23, 59, 59).atZone(BRAZIL_ZONE).toInstant();

        DashboardResponse response = dashboardService.getDashboardData(startUtc, endUtc);

        return ResponseEntity.ok(response);
    }
}