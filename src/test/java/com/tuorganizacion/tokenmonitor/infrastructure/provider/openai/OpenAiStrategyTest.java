package com.tuorganizacion.tokenmonitor.infrastructure.provider.openai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenAiStrategyTest {

    private OpenAiStrategy strategy;

    @BeforeEach
    void setUp() {
        // Nuestro nuevo diseño es limpio, no requiere mocks ni configuraciones complejas
        strategy = new OpenAiStrategy();
    }

    @Test
    void shouldReturnCorrectProviderId() {
        assertEquals("openai", strategy.getProviderId(), "El ID del proveedor debe ser 'openai'");
    }

    @Test
    void shouldCalculateCostCorrectly() {
        // GIVEN
        int inputTokens = 1500;
        int outputTokens = 500;
        String model = "gpt-4o"; // Actualmente el precio está hardcodeado, pero el modelo se pasa por contrato

        // WHEN
        BigDecimal calculatedCost = strategy.calculate(model, inputTokens, outputTokens);

        // THEN
        // Fórmula: (1500 * 0.000005) + (500 * 0.000015)
        // In = 0.0075 | Out = 0.0075 | Total = 0.0150
        BigDecimal expectedCost = new BigDecimal("0.0150"); 
        
        // Usamos compareTo para ignorar problemas de escala (ej. 0.0150 vs 0.015000)
        assertEquals(0, expectedCost.compareTo(calculatedCost), "El costo matemático calculado es incorrecto");
    }
}
