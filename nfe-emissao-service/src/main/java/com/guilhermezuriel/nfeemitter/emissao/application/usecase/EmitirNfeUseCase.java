package com.guilhermezuriel.nfeemitter.emissao.application.usecase;

import com.guilhermezuriel.nfe.commons.utils.UfIbge;
import com.guilhermezuriel.nfeemitter.emissao.application.mapper.NfeApplicationMapper;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.EmitirNfeCommand;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.NfeResult;
import com.guilhermezuriel.nfeemitter.emissao.application.port.out.EmpresaRepository;
import com.guilhermezuriel.nfeemitter.emissao.application.port.out.NfeEventPublisher;
import com.guilhermezuriel.nfeemitter.emissao.application.port.out.NfeRepository;
import com.guilhermezuriel.nfeemitter.emissao.application.port.out.NfeSequenceGenerator;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Empresa;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.enums.StatusNfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.service.CalculoTotaisService;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.ChaveAcesso;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmitirNfeUseCase {

    private final NfeRepository nfeRepository;
    private final EmpresaRepository empresaRepository;
    private final NfeEventPublisher eventPublisher;
    private final NfeSequenceGenerator sequenceGenerator;
    private final CalculoTotaisService calculoTotaisService;

    public EmitirNfeUseCase(NfeRepository nfeRepository,
                            EmpresaRepository empresaRepository,
                            NfeEventPublisher eventPublisher,
                            NfeSequenceGenerator sequenceGenerator) {
        this.nfeRepository = nfeRepository;
        this.empresaRepository = empresaRepository;
        this.eventPublisher = eventPublisher;
        this.sequenceGenerator = sequenceGenerator;
        this.calculoTotaisService = new CalculoTotaisService();
    }

    @Transactional
    public NfeResult executar(EmitirNfeCommand command) {
        Empresa emitente = empresaRepository.buscarOuCriar(
                NfeApplicationMapper.toEmpresa(command.emitente()));
        Empresa destinatario = empresaRepository.buscarOuCriar(
                NfeApplicationMapper.toEmpresa(command.destinatario()));

        long numero = sequenceGenerator.proximo();
        LocalDateTime dataEmissao = LocalDateTime.now();
        int codigoUf = UfIbge.getCodigo(emitente.getUf());

        ChaveAcesso chaveAcesso = ChaveAcesso.gerar(
                codigoUf, dataEmissao, emitente.getCnpj().valor(),
                command.serie(), numero);

        Nfe nfe = new Nfe();
        nfe.setNumero(numero);
        nfe.setSerie(command.serie());
        nfe.setChaveAcesso(chaveAcesso);
        nfe.setDataEmissao(dataEmissao);
        nfe.setNaturezaOperacao(command.naturezaOperacao());
        nfe.setStatus(StatusNfe.PENDENTE);
        nfe.setEmitente(emitente);
        nfe.setDestinatario(destinatario);
        nfe.setItens(NfeApplicationMapper.toNfeItens(command.itens()));

        calculoTotaisService.calcular(nfe);

        Nfe salva = nfeRepository.salvar(nfe);
        eventPublisher.publicarNfeEmitida(salva);

        return NfeApplicationMapper.toResult(salva);
    }
}
