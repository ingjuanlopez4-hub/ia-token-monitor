package com.tuorganizacion.tokenmonitor.infrastructure.persistence.repository;

import com.tuorganizacion.tokenmonitor.infrastructure.persistence.entity.TokenTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenTransactionRepository extends JpaRepository<TokenTransactionEntity, Long> {
}
