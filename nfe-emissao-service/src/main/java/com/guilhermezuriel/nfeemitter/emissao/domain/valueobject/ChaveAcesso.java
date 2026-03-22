package com.guilhermezuriel.nfeemitter.emissao.domain.valueobject;

import com.guilhermezuriel.nfe.commons.utils.ChaveAcessoGenerator;
import com.guilhermezuriel.nfeemitter.emissao.domain.exception.NfeValidationException;

import java.time.LocalDateTime;

public record ChaveAcesso(String valor) {

    public ChaveAcesso {
        if (!ChaveAcessoGenerator.isValida(valor)) {
            throw new NfeValidationException("Chave de acesso inválida: deve ter 44 dígitos e dígito verificador correto");
        }
    }

    public static ChaveAcesso gerar(int codigoUf, LocalDateTime dataEmissao, String cnpjEmitente, int serie, long numero) {
        String chave = ChaveAcessoGenerator.gerar(codigoUf, dataEmissao, cnpjEmitente, serie, numero);
        return new ChaveAcesso(chave);
    }
}
