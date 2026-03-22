package com.guilhermezuriel.nfeemitter.emissao.application.port.in;

import com.guilhermezuriel.nfeemitter.emissao.domain.enums.NaturezaOperacao;

import java.math.BigDecimal;
import java.util.List;

public record EmitirNfeCommand(

        EmpresaData emitente,
        EmpresaData destinatario,
        NaturezaOperacao naturezaOperacao,
        Integer serie,
        List<ItemData> itens

) {

    public record EmpresaData(
            String cnpj,
            String razaoSocial,
            String nomeFantasia,
            String inscricaoEstadual,
            String endereco,
            String municipio,
            String uf,
            String cep,
            String telefone,
            String regimeTributario
    ) {}

    public record ItemData(
            String codigoProduto,
            String descricao,
            String ncm,
            String unidade,
            BigDecimal quantidade,
            BigDecimal valorUnitario
    ) {}
}
