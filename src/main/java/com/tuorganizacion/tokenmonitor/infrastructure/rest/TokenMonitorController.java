package com.tuorganizacion.tokenmonitor.infrastructure.rest;

import com.tuorganizacion.tokenmonitor.domain.model.CostCalculationResult;
import com.tuorganizacion.tokenmonitor.domain.service.CostCalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/monitor")
public class TokenMonitorController {

    private final CostCalculatorService costCalculatorService;

    public TokenMonitorController(CostCalculatorService costCalculatorService) {
        this.costCalculatorService = costCalculatorService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<CostCalculationResult> getCost(
            @RequestParam String provider,
            @RequestParam int input,
            @RequestParam int output,
            @RequestParam String model) {
        
        return ResponseEntity.ok(
            costCalculatorService.calculateCost(provider, input, output, model)
        );
    }
}
