package com.tuorganizacion.tokenmonitor.infrastructure.rest.controller;

import com.tuorganizacion.tokenmonitor.infrastructure.rest.dto.TokenUsagePayload;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/tokens")
public class TokenController {

    // El @Valid es la magia que activa tus reglas del Payload
    @PostMapping("/usage")
    public ResponseEntity<String> registerUsage(
            @Valid @RequestBody TokenUsagePayload payload,
            Principal principal) {

        // 1. Identificar quién hace la petición desde el JWT
        String userEmail = principal.getName();

        // 2. Calcular el total usando los nuevos nombres de tus variables
        int totalTokens = payload.getTokensInput() + payload.getTokensOutput();

        // 3. Imprimir en consola para verificar
        System.out.println("🚨 NUEVO GASTO REGISTRADO 🚨");
        System.out.println("Usuario: " + userEmail);
        System.out.println("Proveedor IA: " + payload.getProviderId());
        System.out.println("Modelo: " + payload.getModel());
        System.out.println("Tokens de entrada: " + payload.getTokensInput());
        System.out.println("Tokens de salida: " + payload.getTokensOutput());
        System.out.println("Total gastado: " + totalTokens);
        System.out.println("-----------------------------------");

        // 4. Responder al cliente
        return ResponseEntity.ok("Consumo de " + totalTokens + " tokens registrado exitosamente.");
    }
}
