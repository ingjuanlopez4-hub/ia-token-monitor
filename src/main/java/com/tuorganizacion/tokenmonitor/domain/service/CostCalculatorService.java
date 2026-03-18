package com.tuorganizacion.tokenmonitor.domain.service;

import com.tuorganizacion.tokenmonitor.domain.model.CostCalculationResult;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CostCalculatorService {

    private static final Logger log = LoggerFactory.getLogger(CostCalculatorService.class);
    private final Map<String, LlmProviderStrategy> strategies;

    public CostCalculatorService(List<LlmProviderStrategy> strategyList) {
        // Usamos la interfaz explícita para evitar problemas con los Proxies de Spring
        this.strategies = strategyList.stream()
                .collect(Collectors.toUnmodifiableMap(
                        s -> s.getProviderId().toLowerCase(),
                        s -> s
                ));
    }

    @PostConstruct
    public void init() {
        // Esto nos confirmará en la terminal si Spring inyectó OpenAI correctamente
        log.info(">>> Estrategias de IA registradas en el Monitor: {} <<<", strategies.keySet());
    }

    public CostCalculationResult calculateCost(String providerId, int inputTokens, int outputTokens, String model) {
        LlmProviderStrategy strategy = Optional.ofNullable(strategies.get(providerId.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no soportado: " + providerId));

        BigDecimal totalCost = strategy.calculate(model, inputTokens, outputTokens);

        return new CostCalculationResult(providerId, model, inputTokens, outputTokens, totalCost);
    }
}
