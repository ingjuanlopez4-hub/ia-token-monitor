package com.tuorganizacion.tokenmonitor.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class LlmPricingConfig {

    /**
     * Definimos el Record con los nombres exactos que busca la estrategia.
     */
    public record ModelPricing(double pricePer1kInputTokens, double pricePer1kOutputTokens) {}

    @Bean
    public Map<String, ModelPricing> prices() {
        return Map.of(
            "gpt-4", new ModelPricing(0.03, 0.06),
            "gpt-3.5-turbo", new ModelPricing(0.0015, 0.002)
        );
    }
}
