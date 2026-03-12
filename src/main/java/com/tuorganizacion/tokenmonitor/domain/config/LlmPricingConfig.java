package com.tuorganizacion.tokenmonitor.domain.config;

import java.util.Map;

public record LlmPricingConfig(Map<String, ModelPricing> prices) {
    public record ModelPricing(double pricePer1kInputTokens, double pricePer1kOutputTokens) {}
}
