package com.tuorganizacion.tokenmonitor.infrastructure.provider.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.tokenmonitor.domain.TokenUsage;
import com.tuorganizacion.tokenmonitor.domain.config.LlmPricingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OpenAiStrategyTest {
    private OpenAiStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new OpenAiStrategy(new ObjectMapper(), new LlmPricingConfig());
    }

    @Test
    void shouldExtractUsageCorrectly() {
        String json = "{\"model\": \"gpt-4\", \"usage\": {\"prompt_tokens\": 10, \"completion_tokens\": 5}}";
        TokenUsage usage = strategy.extractUsage(json);
        assertEquals("gpt-4", usage.modelName());
        assertEquals(10, usage.inputTokens());
        assertEquals(5, usage.outputTokens());
    }

    @Test
    void shouldCalculateCostCorrectly() {
        // Forzamos el uso de variables locales int para que no haya duda de tipos
        String model = "gpt-4";
        int in = 1000;
        int out = 500;
        TokenUsage usage = new TokenUsage(model, in, out);
        double cost = strategy.calculateCost(usage);
        assertEquals(0.06, cost, 0.0001);
    }
}
