package com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record NfeResponse(

        UUID id,
        Long numero,
        Integer serie,
        String chaveAcesso,
        String status,
        String naturezaOperacao,
        String cnpjEmitente,
        String cnpjDestinatario,
        BigDecimal valorTotalProdutos,
        BigDecimal valorTotalNfe,
        int quantidadeItens,
        LocalDateTime dataEmissao

) {}
