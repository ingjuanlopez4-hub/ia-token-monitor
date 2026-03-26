package com.tuorganizacion.tokenmonitor.infrastructure.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class TokenUsagePayload {
    @NotBlank(message = "El modelo no puede estar vacío")
    private String model;

    @Min(value = 0)
    private int tokensInput;

    @Min(value = 0)
    private int tokensOutput;

    private String providerId;
    private String rawJsonResponse;

    // Getters
    public String getModel() { return model; }
    public int getTokensInput() { return tokensInput; }
    public int getTokensOutput() { return tokensOutput; }
    public String getProviderId() { return providerId; }
    public String getRawJsonResponse() { return rawJsonResponse; }

    // Setters
    public void setModel(String model) { this.model = model; }
    public void setTokensInput(int tokensInput) { this.tokensInput = tokensInput; }
    public void setTokensOutput(int tokensOutput) { this.tokensOutput = tokensOutput; }
    public void setProviderId(String providerId) { this.providerId = providerId; }
    public void setRawJsonResponse(String rawJsonResponse) { this.rawJsonResponse = rawJsonResponse; }
}
