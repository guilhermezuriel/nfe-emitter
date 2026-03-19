package com.guilhermezuriel.nfe.commons.dto;

import com.guilhermezuriel.nfe.commons.domain.enums.UnidadeMedida;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Item/produto dentro de uma NF-e.
 * Cada NF-e pode conter de 1 a 990 itens (limite SEFAZ).
 */
public record NfeItemDTO(

        UUID id,

        @Min(value = 1, message = "Número do item deve ser maior que zero")
        int numeroItem,

        @NotBlank(message = "Código do produto é obrigatório")
        @Size(max = 60, message = "Código do produto deve ter no máximo 60 caracteres")
        String codigoProduto,

        @NotBlank(message = "Descrição do produto é obrigatória")
        @Size(max = 120, message = "Descrição deve ter no máximo 120 caracteres")
        String descricao,

        @NotBlank(message = "NCM é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "NCM deve conter exatamente 8 dígitos")
        String ncm,

        @NotBlank(message = "CFOP é obrigatório")
        @Pattern(regexp = "\\d{4}", message = "CFOP deve conter exatamente 4 dígitos")
        String cfop,

        @NotNull(message = "Unidade de medida é obrigatória")
        UnidadeMedida unidade,

        @NotNull(message = "Quantidade é obrigatória")
        @DecimalMin(value = "0.0001", message = "Quantidade deve ser maior que zero")
        BigDecimal quantidade,

        @NotNull(message = "Valor unitário é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor unitário deve ser maior que zero")
        BigDecimal valorUnitario,

        BigDecimal valorTotal,

        ImpostoDTO imposto

) {
    /**
     * Calcula o valor total do item (quantidade × valor unitário).
     */
    public BigDecimal calcularValorTotal() {
        if (quantidade == null || valorUnitario == null) {
            return BigDecimal.ZERO;
        }
        return quantidade.multiply(valorUnitario);
    }
}