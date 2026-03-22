package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.repository;

import com.guilhermezuriel.nfeemitter.emissao.application.port.out.NfeRepository;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.ChaveAcesso;
import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity.NfeJpaEntity;
import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.mapper.NfePersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaNfeRepository implements NfeRepository {

    private final SpringDataNfeRepository springDataRepo;
    private final NfePersistenceMapper mapper;

    public JpaNfeRepository(SpringDataNfeRepository springDataRepo, NfePersistenceMapper mapper) {
        this.springDataRepo = springDataRepo;
        this.mapper = mapper;
    }

    @Override
    public Nfe salvar(Nfe nfe) {
        NfeJpaEntity entity = mapper.toJpaEntity(nfe);
        return mapper.toDomain(springDataRepo.save(entity));
    }

    @Override
    public Optional<Nfe> buscarPorId(UUID id) {
        return springDataRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Nfe> buscarPorChaveAcesso(ChaveAcesso chave) {
        return springDataRepo.findByChaveAcesso(chave.valor()).map(mapper::toDomain);
    }
}
