package com.tuorganizacion.tokenmonitor.infrastructure.provider.openai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.tokenmonitor.domain.LlmProviderStrategy;
import com.tuorganizacion.tokenmonitor.domain.TokenUsage;
import com.tuorganizacion.tokenmonitor.domain.config.LlmPricingConfig;
import org.springframework.stereotype.Component;

@Component
public class OpenAiStrategy implements LlmProviderStrategy {

    private final ObjectMapper objectMapper;
    private final LlmPricingConfig pricingConfig;

    public OpenAiStrategy(ObjectMapper objectMapper, LlmPricingConfig pricingConfig) {
        this.objectMapper = objectMapper;
        this.pricingConfig = pricingConfig;
    }

    @Override
    public String getProviderId() {
        return "OPENAI";
    }

    @Override
    public TokenUsage extractUsage(String rawResponse) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            String model = root.path("model").asText();
            JsonNode usage = root.path("usage");
            
            // Extracción correcta: String, int, int
            return new TokenUsage(
                model,
                usage.path("prompt_tokens").asInt(),
                usage.path("completion_tokens").asInt()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error parseando respuesta de OpenAI", e);
        }
    }

    @Override
    public double calculateCost(TokenUsage usage) {
        LlmPricingConfig.ModelPricing pricing = pricingConfig.prices().get(usage.modelName());
        
        if (pricing == null) {
            return 0.0;
        }

        double inputCost = (usage.inputTokens() / 1000.0) * pricing.pricePer1kInputTokens();
        double outputCost = (usage.outputTokens() / 1000.0) * pricing.pricePer1kOutputTokens();
        
        return inputCost + outputCost;
    }
}
