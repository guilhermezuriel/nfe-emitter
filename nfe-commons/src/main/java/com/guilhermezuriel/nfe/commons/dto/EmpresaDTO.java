package com.nfe.commons.dto;

import com.guilhermezuriel.nfe.commons.domain.enums.RegimeTributario;
import com.nfe.commons.domain.RegimeTributario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Dados da empresa participante da NF-e (emitente ou destinatário).
 */
public record EmpresaDTO(

        UUID id,

        @NotBlank(message = "Razão social é obrigatória")
        @Size(max = 200, message = "Razão social deve ter no máximo 200 caracteres")
        String razaoSocial,

        @Size(max = 60, message = "Nome fantasia deve ter no máximo 60 caracteres")
        String nomeFantasia,

        @NotBlank(message = "CNPJ é obrigatório")
        @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos")
        String cnpj,

        @NotBlank(message = "Inscrição estadual é obrigatória")
        String inscricaoEstadual,

        @NotBlank(message = "Endereço é obrigatório")
        String endereco,

        @NotBlank(message = "Município é obrigatório")
        String municipio,

        @NotBlank(message = "UF é obrigatória")
        @Size(min = 2, max = 2, message = "UF deve ter exatamente 2 caracteres")
        String uf,

        @Pattern(regexp = "\\d{8}", message = "CEP deve conter exatamente 8 dígitos")
        String cep,

        String telefone,

        RegimeTributario regimeTributario

) {
    /**
     * Construtor compacto para criação via API (sem ID).
     */
    public static EmpresaDTO novo(String razaoSocial, String nomeFantasia, String cnpj,
                                  String inscricaoEstadual, String endereco, String municipio,
                                  String uf, String cep, String telefone,
                                  RegimeTributario regimeTributario) {
        return new EmpresaDTO(null, razaoSocial, nomeFantasia, cnpj, inscricaoEstadual,
                endereco, municipio, uf, cep, telefone, regimeTributario);
    }
}