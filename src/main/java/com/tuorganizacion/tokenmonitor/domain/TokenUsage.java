package com.tuorganizacion.tokenmonitor.domain;

public record TokenUsage(int inputTokens, int outputTokens, String modelName) {
    public int totalTokens() {
        return inputTokens + outputTokens;
    }
}
