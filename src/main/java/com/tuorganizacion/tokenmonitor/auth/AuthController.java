package com.tuorganizacion.tokenmonitor.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.bind.annotation.PostMapping;
import org.springframework.web.bind.bind.annotation.RequestBody;
import org.springframework.web.bind.bind.annotation.RequestMapping;
import org.springframework.web.bind.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDto.AuthResponse> register(@RequestBody AuthDto.RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto.AuthResponse> login(@RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
