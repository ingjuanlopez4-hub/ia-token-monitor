package com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "token_usage_logs")
public class TokenUsageLog {

    @Id
    private UUID id;

    @Column(name = "time", nullable = false)
    private ZonedDateTime time;

    @Column(name = "provider_id", nullable = false, length = 50)
    private String providerId;

    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

    @Column(name = "input_tokens", nullable = false)
    private int inputTokens;

    @Column(name = "output_tokens", nullable = false)
    private int outputTokens;

    @Column(name = "total_cost_usd", nullable = false)
    private double totalCostUsd;

    // Constructor vacío requerido por JPA
    protected TokenUsageLog() {}

    public TokenUsageLog(ZonedDateTime time, String providerId, String modelName, int inputTokens, int outputTokens, double totalCostUsd) {
        this.id = UUID.randomUUID();
        this.time = time;
        this.providerId = providerId;
        this.modelName = modelName;
        this.inputTokens = inputTokens;
        this.outputTokens = outputTokens;
        this.totalCostUsd = totalCostUsd;
    }

    // Getters
    public UUID getId() { return id; }
    public ZonedDateTime getTime() { return time; }
    public String getProviderId() { return providerId; }
    public String getModelName() { return modelName; }
    public int getInputTokens() { return inputTokens; }
    public int getOutputTokens() { return outputTokens; }
    public double getTotalCostUsd() { return totalCostUsd; }
}
