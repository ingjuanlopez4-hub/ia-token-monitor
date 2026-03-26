package com.tuorganizacion.tokenmonitor.auth;

// Utilizar 'records' es la forma más moderna, inmutable y limpia de manejar DTOs en Java actual.
public class AuthDto {
    
    public record RegisterRequest(
            String email, 
            String password
    ) {}

    public record LoginRequest(
            String email, 
            String password
    ) {}

    public record AuthResponse(
            String token
    ) {}
}
