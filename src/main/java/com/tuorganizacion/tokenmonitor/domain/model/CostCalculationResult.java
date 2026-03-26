package com.tuorganizacion.tokenmonitor.domain.model;
import java.math.BigDecimal;
public record CostCalculationResult(
        String providerId, String model, int inputTokens, int outputTokens, BigDecimal totalCost
) {}
