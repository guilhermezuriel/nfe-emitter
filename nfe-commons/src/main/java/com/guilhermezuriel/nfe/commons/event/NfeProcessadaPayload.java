package com.guilhermezuriel.nfe.commons.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Payload do evento publicado quando o Spring Batch finaliza o processamento.
 * Consumido pelo serviço de PDF para geração do DANFE.
 *
 * Tópico: nfe.processada
 */
public record NfeProcessadaPayload(

        UUID nfeId,

        String chaveAcesso,

        BigDecimal valorTotalProdutos,

        BigDecimal valorTotalImpostos,

        BigDecimal valorTotalNfe,

        LocalDateTime dataProcessamento,

        long tempoProcessamentoMs

) {
}
