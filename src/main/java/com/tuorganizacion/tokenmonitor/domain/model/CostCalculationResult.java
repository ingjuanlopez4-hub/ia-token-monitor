package com.tuorganizacion.tokenmonitor.domain.model;

import java.math.BigDecimal;

/**
 * Resultado inmutable del cálculo de tokens.
 */
public record CostCalculationResult(
    String providerId,
    String model,
    int inputTokens,
    int outputTokens,
    BigDecimal totalCost
) {}
