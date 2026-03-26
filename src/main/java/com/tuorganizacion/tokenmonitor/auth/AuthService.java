package com.tuorganizacion.tokenmonitor.auth;

import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.UsuarioEntity;
import com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository.UsuarioRepository;
import com.tuorganizacion.tokenmonitor.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        // Validar si el correo ya existe (podrías lanzar una excepción personalizada aquí)
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Crear el nuevo usuario encriptando la contraseña
        UsuarioEntity user = new UsuarioEntity(
                request.email(),
                passwordEncoder.encode(request.password()),
                "ROLE_USER" // Rol por defecto
        );

        usuarioRepository.save(user);

        // Generar el token JWT
        String jwtToken = jwtService.generateToken(user);
        return new AuthDto.AuthResponse(jwtToken);
    }

    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        // Spring Security valida internamente las credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Si pasa la autenticación, buscamos el usuario y generamos su token
        UsuarioEntity user = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(user);
        return new AuthDto.AuthResponse(jwtToken);
    }
}
