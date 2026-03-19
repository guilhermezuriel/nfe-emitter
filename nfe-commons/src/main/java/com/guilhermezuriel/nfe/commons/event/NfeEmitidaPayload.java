package com.guilhermezuriel.nfe.commons.event;



import com.guilhermezuriel.nfe.commons.domain.enums.NaturezaOperacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Payload do evento publicado quando uma NF-e é emitida.
 * Consumido pelo Spring Batch para processamento de impostos.
 *
 * Tópico: nfe.emitida
 */
public record NfeEmitidaPayload(

        UUID nfeId,

        Long numero,

        Integer serie,

        String chaveAcesso,

        String cnpjEmitente,

        String cnpjDestinatario,

        String ufEmitente,

        String ufDestinatario,

        NaturezaOperacao naturezaOperacao,

        BigDecimal valorTotalProdutos,

        int quantidadeItens,

        LocalDateTime dataEmissao

) {
    public boolean isInterestadual() {
        return !ufEmitente.equalsIgnoreCase(ufDestinatario);
    }
}