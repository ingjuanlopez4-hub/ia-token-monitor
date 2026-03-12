package com.tuorganizacion.tokenmonitor.domain.service;

import com.tuorganizacion.tokenmonitor.domain.LlmProviderStrategy;
import com.tuorganizacion.tokenmonitor.domain.TokenUsage;
import com.tuorganizacion.tokenmonitor.domain.model.BillingRecord;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CostCalculatorService {
    private final Map<String, LlmProviderStrategy> strategies;

    public CostCalculatorService(List<LlmProviderStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(LlmProviderStrategy::getProviderId, s -> s));
    }

    public BillingRecord processTransaction(String providerId, String rawResponse) {
        String normalizedProviderId = providerId.toUpperCase();
        LlmProviderStrategy strategy = strategies.get(normalizedProviderId);
        if (strategy == null) throw new IllegalArgumentException("Unsupported: " + normalizedProviderId);
        TokenUsage usage = strategy.extractUsage(rawResponse);
        double cost = strategy.calculateCost(usage);
        return new BillingRecord(normalizedProviderId, usage.modelName(), usage.inputTokens(), usage.outputTokens(), cost);
    }
}
