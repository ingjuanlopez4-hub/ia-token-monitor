package com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository;

import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.TokenUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenUsageLogRepository extends JpaRepository<TokenUsageLog, UUID> {
    
    // Spring Data JPA implementará automáticamente los métodos básicos como save(), findAll(), etc.
    // Aquí podremos agregar consultas personalizadas en el futuro, por ejemplo:
    // List<TokenUsageLog> findByProviderIdAndDateBetween(...)
}
