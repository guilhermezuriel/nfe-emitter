package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.repository;

import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity.EmpresaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataEmpresaRepository extends JpaRepository<EmpresaJpaEntity, UUID> {

    Optional<EmpresaJpaEntity> findByCnpj(String cnpj);
}
