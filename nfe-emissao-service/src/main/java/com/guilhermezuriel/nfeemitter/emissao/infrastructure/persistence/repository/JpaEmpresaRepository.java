package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.repository;

import com.guilhermezuriel.nfeemitter.emissao.application.port.out.EmpresaRepository;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Empresa;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.Cnpj;
import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity.EmpresaJpaEntity;
import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.mapper.NfePersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaEmpresaRepository implements EmpresaRepository {

    private final SpringDataEmpresaRepository springDataRepo;
    private final NfePersistenceMapper mapper;

    public JpaEmpresaRepository(SpringDataEmpresaRepository springDataRepo, NfePersistenceMapper mapper) {
        this.springDataRepo = springDataRepo;
        this.mapper = mapper;
    }

    @Override
    public Empresa salvar(Empresa empresa) {
        EmpresaJpaEntity entity = mapper.toJpaEntity(empresa);
        return mapper.toDomain(springDataRepo.save(entity));
    }

    @Override
    public Optional<Empresa> buscarPorId(UUID id) {
        return springDataRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Empresa> buscarPorCnpj(Cnpj cnpj) {
        return springDataRepo.findByCnpj(cnpj.valor()).map(mapper::toDomain);
    }

    @Override
    public Empresa buscarOuCriar(Empresa empresa) {
        return springDataRepo.findByCnpj(empresa.getCnpj().valor())
                .map(mapper::toDomain)
                .orElseGet(() -> salvar(empresa));
    }
}
