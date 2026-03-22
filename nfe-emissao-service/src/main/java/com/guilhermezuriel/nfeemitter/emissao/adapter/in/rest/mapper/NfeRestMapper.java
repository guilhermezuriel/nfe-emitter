package com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.mapper;

import com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.dto.EmitirNfeRequest;
import com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.dto.NfeResponse;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.EmitirNfeCommand;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.NfeResult;
import com.guilhermezuriel.nfeemitter.emissao.domain.enums.NaturezaOperacao;
import org.springframework.stereotype.Component;

@Component
public class NfeRestMapper {

    public EmitirNfeCommand toCommand(EmitirNfeRequest request) {
        return new EmitirNfeCommand(
                toEmpresaData(request.emitente()),
                toEmpresaData(request.destinatario()),
                NaturezaOperacao.valueOf(request.naturezaOperacao()),
                request.serie(),
                request.itens().stream().map(this::toItemData).toList()
        );
    }

    public NfeResponse toResponse(NfeResult result) {
        return new NfeResponse(
                result.id(),
                result.numero(),
                result.serie(),
                result.chaveAcesso(),
                result.status(),
                result.naturezaOperacao(),
                result.cnpjEmitente(),
                result.cnpjDestinatario(),
                result.valorTotalProdutos(),
                result.valorTotalNfe(),
                result.quantidadeItens(),
                result.dataEmissao()
        );
    }

    private EmitirNfeCommand.EmpresaData toEmpresaData(EmitirNfeRequest.EmpresaRequest req) {
        return new EmitirNfeCommand.EmpresaData(
                req.cnpj(),
                req.razaoSocial(),
                req.nomeFantasia(),
                req.inscricaoEstadual(),
                req.endereco(),
                req.municipio(),
                req.uf(),
                req.cep(),
                req.telefone(),
                req.regimeTributario()
        );
    }

    private EmitirNfeCommand.ItemData toItemData(EmitirNfeRequest.ItemRequest req) {
        return new EmitirNfeCommand.ItemData(
                req.codigoProduto(),
                req.descricao(),
                req.ncm(),
                req.unidade(),
                req.quantidade(),
                req.valorUnitario()
        );
    }
}
