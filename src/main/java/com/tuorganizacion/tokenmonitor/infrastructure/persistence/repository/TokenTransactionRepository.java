package com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository;

import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.TokenTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenTransactionRepository extends JpaRepository<TokenTransactionEntity, Long> {
}
