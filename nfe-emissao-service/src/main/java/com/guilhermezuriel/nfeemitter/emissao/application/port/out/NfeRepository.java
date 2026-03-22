package com.guilhermezuriel.nfeemitter.emissao.application.port.out;

import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.ChaveAcesso;

import java.util.Optional;
import java.util.UUID;

public interface NfeRepository {

    Nfe salvar(Nfe nfe);

    Optional<Nfe> buscarPorId(UUID id);

    Optional<Nfe> buscarPorChaveAcesso(ChaveAcesso chave);
}
