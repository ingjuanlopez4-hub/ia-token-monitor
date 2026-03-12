package com.tuorganizacion.tokenmonitor.domain.model;

public record BillingRecord(
    String providerId,
    String modelName,
    int inputTokens,
    int outputTokens,
    double totalCostUsd
) {
    public int totalTokens() {
        return inputTokens + outputTokens;
    }
}
