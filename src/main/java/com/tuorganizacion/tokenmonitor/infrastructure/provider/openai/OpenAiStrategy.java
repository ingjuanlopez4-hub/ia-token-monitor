package com.tuorganizacion.tokenmonitor.infrastructure.provider.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuorganizacion.tokenmonitor.domain.LlmProviderStrategy;
import com.tuorganizacion.tokenmonitor.domain.TokenUsage;
import com.tuorganizacion.tokenmonitor.domain.config.LlmPricingConfig;
import com.tuorganizacion.tokenmonitor.domain.exception.TokenParsingException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OpenAiStrategy implements LlmProviderStrategy {

    private static final String PROVIDER_ID = "OPENAI";
    
    private final ObjectMapper objectMapper;
    private final LlmPricingConfig pricingConfig;

    public OpenAiStrategy(ObjectMapper objectMapper, LlmPricingConfig pricingConfig) {
        this.objectMapper = objectMapper;
        this.pricingConfig = pricingConfig;
    }

    @Override
    public String getProviderId() {
        return PROVIDER_ID;
    }

    @Override
    public TokenUsage extractUsage(String rawResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(rawResponse);
            
            String modelName = Optional.ofNullable(rootNode.get("model"))
                    .map(JsonNode::asText)
                    .orElse("unknown");

            JsonNode usageNode = rootNode.get("usage");
            if (usageNode == null) {
                throw new TokenParsingException("El nodo 'usage' no se encontró en la respuesta de OpenAI.", null);
            }

            int inputTokens = usageNode.path("prompt_tokens").asInt(0);
            int outputTokens = usageNode.path("completion_tokens").asInt(0);

            return new TokenUsage(inputTokens, outputTokens, modelName);

        } catch (JsonProcessingException e) {
            throw new TokenParsingException("Error al parsear el JSON de la respuesta de OpenAI.", e);
        }
    }

    @Override
    public double calculateCost(TokenUsage usage) {
        LlmPricingConfig.ModelPricing modelPricing = pricingConfig.prices().get(usage.modelName());
        
        if (modelPricing == null) {
            return 0.0;
        }

        double inputCost = (usage.inputTokens() / 1000.0) * modelPricing.pricePer1kInputTokens();
        double outputCost = (usage.outputTokens() / 1000.0) * modelPricing.pricePer1kOutputTokens();

        return inputCost + outputCost;
    }
}
