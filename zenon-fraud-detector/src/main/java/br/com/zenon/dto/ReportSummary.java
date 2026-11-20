package br.com.zenon.dto;

import java.math.BigDecimal;

public record ReportSummary(
        long totalLines,
        long totalFrauds,
        BigDecimal totalAmount
) { }
