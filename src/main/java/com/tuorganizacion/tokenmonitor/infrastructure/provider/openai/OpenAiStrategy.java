package com.tuorganizacion.tokenmonitor.infrastructure.provider.openai;

import com.tuorganizacion.tokenmonitor.domain.service.LlmProviderStrategy;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class OpenAiStrategy implements LlmProviderStrategy {
    
    @Override
    public String getProviderId() { 
        return "openai"; 
    }

    @Override
    public BigDecimal calculate(String model, int inputTokens, int outputTokens) {
        BigDecimal inputPrice = new BigDecimal("0.000005");
        BigDecimal outputPrice = new BigDecimal("0.000015");
        
        return inputPrice.multiply(BigDecimal.valueOf(inputTokens))
                .add(outputPrice.multiply(BigDecimal.valueOf(outputTokens)));
    }
}
