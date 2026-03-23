package com.tuorganizacion.tokenmonitor.domain.service;

import java.math.BigDecimal;

public interface LlmProviderStrategy {
    String getProviderId();
    BigDecimal calculate(String model, int inputTokens, int outputTokens);
}
