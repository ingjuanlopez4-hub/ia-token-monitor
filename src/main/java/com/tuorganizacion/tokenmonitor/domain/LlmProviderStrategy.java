package com.tuorganizacion.tokenmonitor.domain;

public interface LlmProviderStrategy {
    String getProviderId();
    TokenUsage extractUsage(String rawResponse);
    double calculateCost(TokenUsage usage);
}
