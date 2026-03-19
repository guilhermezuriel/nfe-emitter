package com.guilhermezuriel.nfe.commons.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Alíquotas padrão dos impostos utilizadas no cálculo pelo Spring Batch.
 *
 * Em um sistema real, estas alíquotas viriam de uma tabela dinâmica
 * ou serviço externo (SEFAZ). Aqui usamos valores fixos simplificados
 * para fins de demonstração.
 *
 * Todas as alíquotas são expressas em percentual (ex: 18.00 = 18%).
 */
public final class AliquotasImposto {

    private AliquotasImposto() {}

    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    // ── ICMS (Imposto sobre Circulação de Mercadorias) ──
    /** Alíquota interna padrão (operações dentro do mesmo estado) */
    public static final BigDecimal ICMS_ALIQUOTA_INTERNA = new BigDecimal("18.00");

    /** Alíquota interestadual Sul/Sudeste → demais regiões */
    public static final BigDecimal ICMS_ALIQUOTA_INTERESTADUAL_7 = new BigDecimal("7.00");

    /** Alíquota interestadual demais regiões */
    public static final BigDecimal ICMS_ALIQUOTA_INTERESTADUAL_12 = new BigDecimal("12.00");

    // ── PIS (Programa de Integração Social) ──
    /** Regime não cumulativo (Lucro Real) */
    public static final BigDecimal PIS_ALIQUOTA_NAO_CUMULATIVO = new BigDecimal("1.65");

    /** Regime cumulativo (Lucro Presumido) */
    public static final BigDecimal PIS_ALIQUOTA_CUMULATIVO = new BigDecimal("0.65");

    // ── COFINS (Contribuição para Financiamento da Seguridade Social) ──
    /** Regime não cumulativo (Lucro Real) */
    public static final BigDecimal COFINS_ALIQUOTA_NAO_CUMULATIVO = new BigDecimal("7.60");

    /** Regime cumulativo (Lucro Presumido) */
    public static final BigDecimal COFINS_ALIQUOTA_CUMULATIVO = new BigDecimal("3.00");

    // ── IPI (Imposto sobre Produtos Industrializados) ──
    /** Alíquota padrão simplificada (varia por NCM na vida real) */
    public static final BigDecimal IPI_ALIQUOTA_PADRAO = new BigDecimal("5.00");

    /** Alíquota para itens isentos de IPI */
    public static final BigDecimal IPI_ISENTO = BigDecimal.ZERO;

    // ── UFs do Sul/Sudeste (usadas para definir alíquota interestadual) ──
    private static final String[] UFS_SUL_SUDESTE = {"SP", "RJ", "MG", "ES", "PR", "SC", "RS"};

    /**
     * Determina a alíquota de ICMS baseada na UF de origem e destino.
     */
    public static BigDecimal getAliquotaIcms(String ufOrigem, String ufDestino) {
        if (ufOrigem.equalsIgnoreCase(ufDestino)) {
            return ICMS_ALIQUOTA_INTERNA;
        }
        return isSulSudeste(ufOrigem) ? ICMS_ALIQUOTA_INTERESTADUAL_7 : ICMS_ALIQUOTA_INTERESTADUAL_12;
    }

    private static boolean isSulSudeste(String uf) {
        for (String sulSudeste : UFS_SUL_SUDESTE) {
            if (sulSudeste.equalsIgnoreCase(uf)) {
                return true;
            }
        }
        return false;
    }
}