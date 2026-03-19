package com.guilhermezuriel.nfe.commons.dto;


import com.guilhermezuriel.nfe.commons.domain.enums.NaturezaOperacao;
import com.guilhermezuriel.nfe.commons.domain.enums.NfeStatus;
import com.nfe.commons.dto.EmpresaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO principal da Nota Fiscal Eletrônica.
 * Trafega entre microsserviços via API REST e eventos Kafka.
 */
public record NfeDTO(

        UUID id,

        Long numero,

        Integer serie,

        String chaveAcesso,

        LocalDateTime dataEmissao,

        LocalDateTime dataProcessamento,

        @NotNull(message = "Natureza da operação é obrigatória")
        NaturezaOperacao naturezaOperacao,

        BigDecimal valorTotalProdutos,

        BigDecimal valorTotalImpostos,

        BigDecimal valorTotalNfe,

        NfeStatus status,

        @NotNull(message = "Emitente é obrigatório")
        @Valid
        EmpresaDTO emitente,

        @NotNull(message = "Destinatário é obrigatório")
        @Valid
        EmpresaDTO destinatario,

        @NotEmpty(message = "NF-e deve conter ao menos um item")
        @Valid
        List<NfeItemDTO> itens

) {
    /**
     * Construtor para criação de nova NF-e via API (campos calculados são nulos).
     */
    public static NfeDTO novaEmissao(NaturezaOperacao naturezaOperacao,
                                     EmpresaDTO emitente,
                                     EmpresaDTO destinatario,
                                     List<NfeItemDTO> itens) {
        return new NfeDTO(
                null, null, null, null,
                null, null,
                naturezaOperacao,
                null, null, null,
                null,
                emitente, destinatario, itens
        );
    }

    /**
     * Calcula o valor total dos produtos somando todos os itens.
     */
    public BigDecimal calcularValorTotalProdutos() {
        if (itens == null || itens.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return itens.stream()
                .map(NfeItemDTO::calcularValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula o total de impostos somando os impostos de cada item.
     */
    public BigDecimal calcularValorTotalImpostos() {
        if (itens == null || itens.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return itens.stream()
                .filter(item -> item.imposto() != null)
                .map(item -> item.imposto().totalImpostos())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Verifica se a operação é interna (mesmo estado) ou interestadual.
     */
    public boolean isOperacaoInterna() {
        if (emitente == null || destinatario == null) {
            return true;
        }
        return emitente.uf().equalsIgnoreCase(destinatario.uf());
    }
}