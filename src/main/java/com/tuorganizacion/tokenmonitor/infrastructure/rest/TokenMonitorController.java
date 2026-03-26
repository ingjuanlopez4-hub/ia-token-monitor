package com.tuorganizacion.tokenmonitor.infrastructure.rest;
import com.tuorganizacion.tokenmonitor.domain.model.CostCalculationResult;
import com.tuorganizacion.tokenmonitor.domain.service.CostCalculatorService;
import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.TokenTransactionEntity;
import com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository.TokenTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/monitor")
public class TokenMonitorController {
    private static final Logger log = LoggerFactory.getLogger(TokenMonitorController.class);
    private final CostCalculatorService costCalculatorService;
    private final TokenTransactionRepository repository;

    public TokenMonitorController(CostCalculatorService costCalculatorService, TokenTransactionRepository repository) {
        this.costCalculatorService = costCalculatorService;
        this.repository = repository;
    }

    @GetMapping("/calculate")
    public ResponseEntity<CostCalculationResult> getCost(
            @RequestParam String provider, @RequestParam int input, @RequestParam int output, @RequestParam String model) {
        CostCalculationResult result = costCalculatorService.calculateCost(provider, input, output, model);
        TokenTransactionEntity transaction = new TokenTransactionEntity(
                result.providerId(), result.model(), result.inputTokens(), result.outputTokens(), result.totalCost()
        );
        repository.save(transaction);
        log.info("💾 Transacción guardada en Postgres. ID: {}", transaction.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TokenTransactionEntity>> getAllTransactions() {
        return ResponseEntity.ok(repository.findAll());
    }
}
