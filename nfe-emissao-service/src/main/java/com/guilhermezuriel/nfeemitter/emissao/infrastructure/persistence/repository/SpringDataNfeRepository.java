package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.repository;

import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity.NfeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataNfeRepository extends JpaRepository<NfeJpaEntity, UUID> {

    Optional<NfeJpaEntity> findByChaveAcesso(String chaveAcesso);
}
