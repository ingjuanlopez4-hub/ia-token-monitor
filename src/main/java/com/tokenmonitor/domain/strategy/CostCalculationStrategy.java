package com.tokenmonitor.domain.strategy;

import com.tokenmonitor.domain.model.TokenConsumption;
import java.math.BigDecimal;

/**
 * Defines the contract to calculate the cost of an LLM request.
 * Implements the Strategy pattern to allow dynamic calculations.
 */
public interface CostCalculationStrategy {
    boolean supports(TokenConsumption consumption);
    BigDecimal calculateCost(TokenConsumption consumption);
}
