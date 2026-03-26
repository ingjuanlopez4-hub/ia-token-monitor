package com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String rol; // Ejemplo: "ROLE_USER" o "ROLE_ADMIN"

    // Constructores vacíos y con parámetros requeridos por JPA
    public UsuarioEntity() {}

    public UsuarioEntity(String email, String password, String rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // Getters y Setters básicos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public void setPassword(String password) { this.password = password; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    // --- Métodos obligatorios de la interfaz UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security requiere que los roles sean devueltos como "GrantedAuthority"
        return List.of(new SimpleGrantedAuthority(rol));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        // En nuestro sistema, el correo electrónico actuará como el nombre de usuario
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Por ahora, las cuentas no expiran
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Por ahora, las cuentas no se bloquean
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Por ahora, las credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return true; // Por ahora, todos los usuarios creados están activos
    }
}
