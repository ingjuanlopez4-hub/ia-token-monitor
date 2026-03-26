package com.tokenmonitor.domain.strategy;

import com.tokenmonitor.domain.model.LlmProvider;
import com.tokenmonitor.domain.model.TokenConsumption;
import com.tokenmonitor.domain.strategy.impl.Gpt4CostCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class Gpt4CostCalculatorTest {

    private Gpt4CostCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Gpt4CostCalculator();
    }

    @Test
    void shouldSupportGpt4Models() {
        TokenConsumption consumption = new TokenConsumption(
                "key-123", LlmProvider.OPENAI, "gpt-4-turbo", 100, 100, Instant.now()
        );
        assertTrue(calculator.supports(consumption));
    }

    @Test
    void shouldNotSupportOtherModels() {
        TokenConsumption consumption = new TokenConsumption(
                "key-123", LlmProvider.ANTHROPIC, "claude-3", 100, 100, Instant.now()
        );
        assertFalse(calculator.supports(consumption));
    }

    @Test
    void shouldCalculateCostCorrectly() {
        TokenConsumption consumption = new TokenConsumption(
                "key-123", LlmProvider.OPENAI, "gpt-4-turbo", 500, 1500, Instant.now()
        );
        BigDecimal cost = calculator.calculateCost(consumption);
        assertEquals(0, new BigDecimal("0.050").compareTo(cost), "The calculated cost is incorrect");
    }
    
    @Test
    void shouldThrowExceptionOnInvalidTokenConsumption() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TokenConsumption("", LlmProvider.OPENAI, "gpt-4", 10, 10, Instant.now());
        });
    }
}
