package com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_transactions")
public class TokenTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String providerId;
    private String modelName;
    private Integer inputTokens;
    private Integer outputTokens;
    private BigDecimal totalCost;
    private LocalDateTime timestamp;

    protected TokenTransactionEntity() {}

    public TokenTransactionEntity(String providerId, String modelName, Integer inputTokens, Integer outputTokens, BigDecimal totalCost) {
        this.providerId = providerId;
        this.modelName = modelName;
        this.inputTokens = inputTokens;
        this.outputTokens = outputTokens;
        this.totalCost = totalCost;
        this.timestamp = LocalDateTime.now();
    }

    // 🔥 AQUI ESTAN LOS GETTERS PARA QUE EL JSON SALGA COMPLETO
    public Long getId() { return id; }
    public String getProviderId() { return providerId; }
    public String getModelName() { return modelName; }
    public Integer getInputTokens() { return inputTokens; }
    public Integer getOutputTokens() { return outputTokens; }
    public BigDecimal getTotalCost() { return totalCost; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
