package com.guilhermezuriel.nfe.commons.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Impostos calculados para um item da NF-e.
 * Preenchido pelo Spring Batch (ItemProcessor) na fase de processamento.
 *
 * Impostos cobertos:
 * - ICMS: Imposto sobre Circulação de Mercadorias e Serviços (estadual)
 * - PIS: Programa de Integração Social (federal)
 * - COFINS: Contribuição para o Financiamento da Seguridade Social (federal)
 * - IPI: Imposto sobre Produtos Industrializados (federal)
 */
public record ImpostoDTO(

        UUID id,

        // ── ICMS ──
        BigDecimal icmsBase,
        BigDecimal icmsAliquota,
        BigDecimal icmsValor,

        // ── PIS ──
        BigDecimal pisBase,
        BigDecimal pisAliquota,
        BigDecimal pisValor,

        // ── COFINS ──
        BigDecimal cofinsBase,
        BigDecimal cofinsAliquota,
        BigDecimal cofinsValor,

        // ── IPI ──
        BigDecimal ipiBase,
        BigDecimal ipiAliquota,
        BigDecimal ipiValor

) {
    /**
     * Soma total de todos os impostos deste item.
     */
    public BigDecimal totalImpostos() {
        return safe(icmsValor)
                .add(safe(pisValor))
                .add(safe(cofinsValor))
                .add(safe(ipiValor));
    }

    /**
     * Cria um ImpostoDTO zerado (para itens ainda não processados).
     */
    public static ImpostoDTO zerado() {
        return new ImpostoDTO(
                null,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
        );
    }

    private static BigDecimal safe(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}