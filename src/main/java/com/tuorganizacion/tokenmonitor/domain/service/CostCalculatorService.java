package com.tuorganizacion.tokenmonitor.domain.service;

import com.tuorganizacion.tokenmonitor.domain.model.CostCalculationResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class CostCalculatorService {

    // Récord interno para guardar los precios (por cada 1,000 tokens)
    record ModelPricing(BigDecimal inputPricePer1k, BigDecimal outputPricePer1k) {}

    private final Map<String, Map<String, ModelPricing>> pricingRegistry = new HashMap<>();

    public CostCalculatorService() {
        // 🔵 PRECIOS OPENAI (Generación GPT-5.4 - 2026)
        Map<String, ModelPricing> openAiModels = new HashMap<>();
        openAiModels.put("gpt-5.4", new ModelPricing(new BigDecimal("0.00125"), new BigDecimal("0.0075")));
        openAiModels.put("gpt-5.4-mini", new ModelPricing(new BigDecimal("0.000375"), new BigDecimal("0.00225")));
        openAiModels.put("gpt-5.4-nano", new ModelPricing(new BigDecimal("0.0001"), new BigDecimal("0.000625")));
        pricingRegistry.put("openai", openAiModels);

        // 🟠 PRECIOS ANTHROPIC (Generación Claude 4 - 2026)
        Map<String, ModelPricing> anthropicModels = new HashMap<>();
        anthropicModels.put("claude-opus-4-6", new ModelPricing(new BigDecimal("0.005"), new BigDecimal("0.025")));
        anthropicModels.put("claude-sonnet-4-6", new ModelPricing(new BigDecimal("0.003"), new BigDecimal("0.015")));
        anthropicModels.put("claude-haiku-4-5", new ModelPricing(new BigDecimal("0.001"), new BigDecimal("0.005")));
        pricingRegistry.put("anthropic", anthropicModels);
    }

    public CostCalculationResult calculateCost(String provider, int inputTokens, int outputTokens, String model) {
        String providerKey = provider.toLowerCase();
        String modelKey = model.toLowerCase();

        if (!pricingRegistry.containsKey(providerKey)) {
            throw new IllegalArgumentException("Proveedor no soportado: " + provider);
        }

        Map<String, ModelPricing> providerModels = pricingRegistry.get(providerKey);
        
        if (!providerModels.containsKey(modelKey)) {
            throw new IllegalArgumentException("Modelo no soportado para el proveedor " + provider + ": " + model);
        }

        ModelPricing pricing = providerModels.get(modelKey);

        // Cálculo exacto: (Tokens * PrecioPor1k) / 1000
        BigDecimal inputCost = pricing.inputPricePer1k()
                .multiply(new BigDecimal(inputTokens))
                .divide(new BigDecimal(1000), 6, RoundingMode.HALF_UP);
                
        BigDecimal outputCost = pricing.outputPricePer1k()
                .multiply(new BigDecimal(outputTokens))
                .divide(new BigDecimal(1000), 6, RoundingMode.HALF_UP);
                
        BigDecimal totalCost = inputCost.add(outputCost);

        return new CostCalculationResult(providerKey, modelKey, inputTokens, outputTokens, totalCost);
    }
}
