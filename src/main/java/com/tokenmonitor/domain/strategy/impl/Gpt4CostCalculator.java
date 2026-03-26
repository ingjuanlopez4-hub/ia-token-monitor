package com.tokenmonitor.domain.strategy.impl;

import com.tokenmonitor.domain.model.LlmProvider;
import com.tokenmonitor.domain.model.TokenConsumption;
import com.tokenmonitor.domain.strategy.CostCalculationStrategy;
import java.math.BigDecimal;

/**
 * Implementation of the cost calculation strategy for OpenAI's GPT-4 models.
 */
public class Gpt4CostCalculator implements CostCalculationStrategy {

    private static final BigDecimal INPUT_COST_PER_1K = new BigDecimal("0.01");
    private static final BigDecimal OUTPUT_COST_PER_1K = new BigDecimal("0.03");
    private static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");

    @Override
    public boolean supports(TokenConsumption consumption) {
        return LlmProvider.OPENAI.equals(consumption.provider()) 
                && consumption.model().toLowerCase().startsWith("gpt-4");
    }

    @Override
    public BigDecimal calculateCost(TokenConsumption consumption) {
        BigDecimal inputCost = new BigDecimal(consumption.inputTokens())
                .divide(ONE_THOUSAND)
                .multiply(INPUT_COST_PER_1K);

        BigDecimal outputCost = new BigDecimal(consumption.outputTokens())
                .divide(ONE_THOUSAND)
                .multiply(OUTPUT_COST_PER_1K);

        return inputCost.add(outputCost);
    }
}
