package com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository;

import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
    // Spring Data JPA construirá la consulta SQL automáticamente gracias al nombre del método
    Optional<UsuarioEntity> findByEmail(String email);
    
}
