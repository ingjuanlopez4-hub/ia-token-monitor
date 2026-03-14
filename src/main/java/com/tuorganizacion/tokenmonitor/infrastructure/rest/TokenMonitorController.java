package com.tuorganizacion.tokenmonitor.infrastructure.rest;

import com.tuorganizacion.tokenmonitor.domain.model.BillingRecord;
import com.tuorganizacion.tokenmonitor.domain.service.CostCalculatorService;
import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.TokenUsageLog;
import com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository.TokenUsageLogRepository;
import com.tuorganizacion.tokenmonitor.infrastructure.rest.dto.TokenUsagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/monitor")
public class TokenMonitorController {

    private static final Logger log = LoggerFactory.getLogger(TokenMonitorController.class);
    
    private final CostCalculatorService calculatorService;
    private final TokenUsageLogRepository repository;

    public TokenMonitorController(CostCalculatorService calculatorService, TokenUsageLogRepository repository) {
        this.calculatorService = calculatorService;
        this.repository = repository;
    }

    @PostMapping("/usage")
    public ResponseEntity<BillingRecord> reportUsage(@RequestBody TokenUsagePayload payload) {
        log.info("[API] Incoming usage report for provider: {}", payload.providerId());

        try {
            // 1. Calculate the cost using our domain logic
            BillingRecord record = calculatorService.processTransaction(payload.providerId(), payload.rawJsonResponse());

            // 2. Prepare the database entity
            TokenUsageLog usageLog = new TokenUsageLog(
                    ZonedDateTime.now(ZoneOffset.UTC),
                    payload.providerId(),
                    record.modelName(),
                    record.inputTokens(),
                    record.outputTokens(),
                    record.totalCostUsd()
            );

            // 3. Save to TimeScaleDB
            repository.save(usageLog);
            log.info("[API] Successfully saved billing record to database. Cost: ${}", String.format("%.4f", record.totalCostUsd()));

            // 4. Return success response to the browser extension
            return ResponseEntity.ok(record);

        } catch (Exception e) {
            log.error("[API] Failed to process usage report", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
