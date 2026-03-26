package com.tokenmonitor.domain.model;

import java.time.Instant;

/**
 * Immutable record that encapsulates the token consumption of a request.
 * Being a Record, it is inherently thread-safe.
 */
public record TokenConsumption(
        String apiKey,
        LlmProvider provider,
        String model,
        int inputTokens,
        int outputTokens,
        Instant timestamp
) {
    public TokenConsumption {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("API Key cannot be empty");
        }
        if (inputTokens < 0 || outputTokens < 0) {
            throw new IllegalArgumentException("Tokens cannot be negative");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}
