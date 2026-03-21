package com.guilhermezuriel.nfe.commons.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gerador da chave de acesso da NF-e (44 dígitos numéricos).
 *
 * Estrutura real da chave (padrão SEFAZ):
 * ┌──────────────────────────────────────────────────────────┐
 * │ Pos │ Tam │ Campo                                        │
 * ├──────────────────────────────────────────────────────────┤
 * │  1  │  2  │ Código da UF do emitente                     │
 * │  3  │  4  │ Ano/mês de emissão (AAMM)                    │
 * │  7  │ 14  │ CNPJ do emitente                             │
 * │ 21  │  2  │ Modelo do documento (55 = NF-e)              │
 * │ 23  │  3  │ Série da NF-e                                │
 * │ 26  │  9  │ Número da NF-e                               │
 * │ 35  │  1  │ Tipo de emissão (1 = normal)                 │
 * │ 36  │  8  │ Código numérico aleatório                    │
 * │ 44  │  1  │ Dígito verificador (módulo 11)               │
 * └──────────────────────────────────────────────────────────┘
 */
public final class ChaveAcessoGenerator {

    private ChaveAcessoGenerator() {}

    private static final DateTimeFormatter AAMM = DateTimeFormatter.ofPattern("yyMM");
    private static final String MODELO_NFE = "55";
    private static final String TIPO_EMISSAO_NORMAL = "1";

    /**
     * Gera a chave de acesso de 44 dígitos.
     *
     * @param codigoUf     código IBGE da UF do emitente
     * @param dataEmissao  data de emissão da NF-e
     * @param cnpjEmitente CNPJ do emitente (14 dígitos, sem formatação)
     * @param serie        série da NF-e
     * @param numero       número sequencial da NF-e
     * @return chave de acesso com 44 dígitos
     */
    public static String gerar(int codigoUf, LocalDateTime dataEmissao,
                               String cnpjEmitente, int serie, long numero) {

        String cnpjLimpo = cnpjEmitente.replaceAll("[^0-9]", "");
        if (cnpjLimpo.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter 14 dígitos: " + cnpjEmitente);
        }

        String codigoNumerico = gerarCodigoNumerico();

        String chaveBase = String.format("%02d%s%s%s%03d%09d%s%s",
                codigoUf,
                dataEmissao.format(AAMM),
                cnpjLimpo,
                MODELO_NFE,
                serie,
                numero,
                TIPO_EMISSAO_NORMAL,
                codigoNumerico);

        int digitoVerificador = calcularDigitoVerificador(chaveBase);

        return chaveBase + digitoVerificador;
    }

    /**
     * Valida se uma chave de acesso tem formato correto e dígito verificador válido.
     */
    public static boolean isValida(String chaveAcesso) {
        if (chaveAcesso == null || chaveAcesso.length() != 44) {
            return false;
        }
        if (!chaveAcesso.matches("\\d{44}")) {
            return false;
        }

        String base = chaveAcesso.substring(0, 43);
        int digitoEsperado = chaveAcesso.charAt(43) - '0';

        return calcularDigitoVerificador(base) == digitoEsperado;
    }

    /**
     * Gera código numérico aleatório de 8 dígitos.
     */
    private static String gerarCodigoNumerico() {
        int codigo = (int) (Math.random() * 100_000_000);
        return String.format("%08d", codigo);
    }

    /**
     * Calcula o dígito verificador pelo módulo 11 (pesos de 2 a 9, cíclico).
     */
    private static int calcularDigitoVerificador(String chaveBase) {
        int soma = 0;
        int peso = 2;

        for (int i = chaveBase.length() - 1; i >= 0; i--) {
            soma += (chaveBase.charAt(i) - '0') * peso;
            peso = peso == 9 ? 2 : peso + 1;
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}