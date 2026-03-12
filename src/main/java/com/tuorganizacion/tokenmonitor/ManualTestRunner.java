package com.tuorganizacion.tokenmonitor;

import com.tuorganizacion.tokenmonitor.domain.model.BillingRecord;
import com.tuorganizacion.tokenmonitor.domain.service.CostCalculatorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ManualTestRunner implements CommandLineRunner {
    private final CostCalculatorService calculator;

    public ManualTestRunner(CostCalculatorService calculator) {
        this.calculator = calculator;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n🚀 --- INICIANDO SIMULACIÓN DE COSTOS ---");
        String mockResponse = "{\"model\": \"gpt-4\", \"usage\": {\"prompt_tokens\": 1000, \"completion_tokens\": 500}}";
        try {
            BillingRecord record = calculator.processTransaction("OPENAI", mockResponse);
            System.out.println("✅ Transacción Procesada con Éxito");
            System.out.println("💰 Costo Total: $" + String.format("%.4f", record.totalCostUsd()) + " USD");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        System.out.println("------------------------------------------\n");
    }
}
