package com.guilhermezuriel.nfeemitter.emissao.application.usecase;

import com.guilhermezuriel.nfeemitter.emissao.application.mapper.NfeApplicationMapper;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.NfeResult;
import com.guilhermezuriel.nfeemitter.emissao.application.port.out.NfeRepository;
import com.guilhermezuriel.nfeemitter.emissao.domain.exception.NfeBusinessException;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.ChaveAcesso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BuscarNfeUseCase {

    private final NfeRepository nfeRepository;

    public BuscarNfeUseCase(NfeRepository nfeRepository) {
        this.nfeRepository = nfeRepository;
    }

    @Transactional(readOnly = true)
    public NfeResult buscarPorId(UUID id) {
        return nfeRepository.buscarPorId(id)
                .map(NfeApplicationMapper::toResult)
                .orElseThrow(() -> new NfeBusinessException("NF-e não encontrada: " + id));
    }

    @Transactional(readOnly = true)
    public NfeResult buscarPorChaveAcesso(String chave) {
        return nfeRepository.buscarPorChaveAcesso(new ChaveAcesso(chave))
                .map(NfeApplicationMapper::toResult)
                .orElseThrow(() -> new NfeBusinessException("NF-e não encontrada para chave: " + chave));
    }
}
