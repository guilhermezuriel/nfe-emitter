package com.guilhermezuriel.nfe.commons.domain.enums;

import java.math.BigDecimal;

/**
 * Regime tributário da empresa emitente.
 * Define as alíquotas e regras de cálculo aplicáveis.
 */
public enum RegimeTributario {

    SIMPLES_NACIONAL(1, "Simples Nacional", new BigDecimal("3.95")),
    LUCRO_PRESUMIDO(2, "Lucro Presumido", new BigDecimal("11.33")),
    LUCRO_REAL(3, "Lucro Real", new BigDecimal("9.25"));

    private final int codigo;
    private final String descricao;
    private final BigDecimal aliquotaEfetiva; // alíquota simplificada para PIS+COFINS

    RegimeTributario(int codigo, String descricao, BigDecimal aliquotaEfetiva) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.aliquotaEfetiva = aliquotaEfetiva;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getAliquotaEfetiva() {
        return aliquotaEfetiva;
    }

    public static RegimeTributario fromCodigo(int codigo) {
        for (RegimeTributario regime : values()) {
            if (regime.codigo == codigo) {
                return regime;
            }
        }
        throw new IllegalArgumentException("Código de regime tributário inválido: " + codigo);
    }
}
