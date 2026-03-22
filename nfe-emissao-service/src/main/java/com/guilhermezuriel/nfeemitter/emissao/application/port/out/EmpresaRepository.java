package com.guilhermezuriel.nfeemitter.emissao.application.port.out;

import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Empresa;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.Cnpj;

import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository {

    Empresa salvar(Empresa empresa);

    Optional<Empresa> buscarPorId(UUID id);

    Optional<Empresa> buscarPorCnpj(Cnpj cnpj);

    Empresa buscarOuCriar(Empresa empresa);
}
