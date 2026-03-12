package com.tuorganizacion.tokenmonitor.infrastructure.provider.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.tokenmonitor.domain.TokenUsage;
import com.tuorganizacion.tokenmonitor.domain.config.LlmPricingConfig;
import com.tuorganizacion.tokenmonitor.domain.exception.TokenParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OpenAiStrategyTest {

    private OpenAiStrategy strategy;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        LlmPricingConfig pricingConfig = new LlmPricingConfig(Map.of(
                "gpt-4", new LlmPricingConfig.ModelPricing(0.03, 0.06)
        ));
        strategy = new OpenAiStrategy(objectMapper, pricingConfig);
    }

    @Test
    @DisplayName("Debe extraer correctamente los tokens de una respuesta JSON válida")
    void shouldExtractTokensSuccessfully() {
        String validJsonResponse = """
            {
              "id": "chatcmpl-123",
              "model": "gpt-4",
              "usage": {
                "prompt_tokens": 1000,
                "completion_tokens": 500
              }
            }
            """;

        TokenUsage usage = strategy.extractUsage(validJsonResponse);

        assertEquals("gpt-4", usage.modelName());
        assertEquals(1000, usage.inputTokens());
        assertEquals(500, usage.outputTokens());
    }

    @Test
    @DisplayName("Debe lanzar TokenParsingException si falta el nodo usage")
    void shouldThrowExceptionWhenUsageNodeIsMissing() {
        String invalidJsonResponse = "{\"model\": \"gpt-4\"}";
        assertThrows(TokenParsingException.class, () -> strategy.extractUsage(invalidJsonResponse));
    }

    @Test
    @DisplayName("Debe calcular el costo correctamente")
    void shouldCalculateCostAccurately() {
        TokenUsage usage = new TokenUsage(1000, 500, "gpt-4");
        assertEquals(0.06, strategy.calculateCost(usage), 0.0001);
    }
}
