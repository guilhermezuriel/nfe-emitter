package com.guilhermezuriel.nfeemitter.emissao.domain.valueobject;

import com.guilhermezuriel.nfeemitter.emissao.domain.exception.NfeValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ValorMonetario(BigDecimal valor) {

    public static final ValorMonetario ZERO = new ValorMonetario(BigDecimal.ZERO);

    public ValorMonetario {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new NfeValidationException("Valor monetário não pode ser nulo ou negativo");
        }
        valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static ValorMonetario of(BigDecimal valor) {
        return new ValorMonetario(valor);
    }

    public ValorMonetario somar(ValorMonetario outro) {
        return new ValorMonetario(this.valor.add(outro.valor));
    }

    public ValorMonetario multiplicar(BigDecimal fator) {
        return new ValorMonetario(this.valor.multiply(fator));
    }
}
