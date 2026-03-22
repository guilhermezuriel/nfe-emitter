package com.guilhermezuriel.nfeemitter.emissao.domain.valueobject;

import com.guilhermezuriel.nfe.commons.utils.CnpjValidator;
import com.guilhermezuriel.nfeemitter.emissao.domain.exception.NfeValidationException;

public record Cnpj(String valor) {

    public Cnpj {
        String digits = valor == null ? "" : valor.replaceAll("[^0-9]", "");
        if (!CnpjValidator.isValido(digits)) {
            throw new NfeValidationException("CNPJ inválido: " + valor);
        }
        valor = digits;
    }

    public String formatado() {
        return CnpjValidator.formatar(valor);
    }
}
