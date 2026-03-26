package com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository;

import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String email);
}
