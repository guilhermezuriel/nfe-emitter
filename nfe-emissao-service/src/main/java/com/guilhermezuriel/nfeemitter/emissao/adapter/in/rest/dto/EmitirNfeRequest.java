package com.guilhermezuriel.nfeemitter.emissao.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record EmitirNfeRequest(

        @NotNull @Valid
        EmpresaRequest emitente,

        @NotNull @Valid
        EmpresaRequest destinatario,

        @NotBlank
        String naturezaOperacao,

        @NotNull @Min(1) @Max(999)
        Integer serie,

        @NotNull @Size(min = 1, max = 990)
        List<@Valid ItemRequest> itens

) {

    public record EmpresaRequest(

            @NotBlank @Size(min = 14, max = 18)
            String cnpj,

            @NotBlank @Size(max = 200)
            String razaoSocial,

            @Size(max = 60)
            String nomeFantasia,

            @NotBlank @Size(max = 20)
            String inscricaoEstadual,

            @NotBlank @Size(max = 300)
            String endereco,

            @NotBlank @Size(max = 100)
            String municipio,

            @NotBlank @Size(min = 2, max = 2)
            String uf,

            @Size(min = 8, max = 8)
            String cep,

            @Size(max = 20)
            String telefone,

            @NotBlank
            String regimeTributario

    ) {}

    public record ItemRequest(

            @NotBlank @Size(max = 60)
            String codigoProduto,

            @NotBlank @Size(max = 120)
            String descricao,

            @NotBlank @Size(min = 8, max = 8)
            String ncm,

            @NotBlank @Size(max = 10)
            String unidade,

            @NotNull @DecimalMin("0.0001")
            BigDecimal quantidade,

            @NotNull @DecimalMin("0.0001")
            BigDecimal valorUnitario

    ) {}
}
