package com.guilhermezuriel.nfe.commons.utils;

public final class CnpjValidator {

    private CnpjValidator() {}

    private static final int[] PESOS_PRIMEIRO = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_SEGUNDO = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     * Valida um CNPJ (aceita com ou sem formatação).
     *
     * @param cnpj CNPJ a ser validado (ex: "11222333000181" ou "11.222.333/0001-81")
     * @return true se o CNPJ é válido
     */
    public static boolean isValido(String cnpj) {
        if (cnpj == null) {
            return false;
        }

        String digits = cnpj.replaceAll("[^0-9]", "");

        if (digits.length() != 14) {
            return false;
        }

        // Rejeita CNPJs com todos os dígitos iguais (ex: 11111111111111)
        if (digits.chars().distinct().count() == 1) {
            return false;
        }

        int primeiroDigito = calcularDigito(digits, PESOS_PRIMEIRO, 12);
        int segundoDigito = calcularDigito(digits, PESOS_SEGUNDO, 13);

        return digits.charAt(12) - '0' == primeiroDigito
                && digits.charAt(13) - '0' == segundoDigito;
    }

    /**
     * Formata um CNPJ no padrão XX.XXX.XXX/XXXX-XX.
     *
     * @param cnpj CNPJ com 14 dígitos numéricos
     * @return CNPJ formatado
     * @throws IllegalArgumentException se o CNPJ não tem 14 dígitos
     */
    public static String formatar(String cnpj) {
        String digits = cnpj.replaceAll("[^0-9]", "");
        if (digits.length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter 14 dígitos: " + cnpj);
        }
        return String.format("%s.%s.%s/%s-%s",
                digits.substring(0, 2),
                digits.substring(2, 5),
                digits.substring(5, 8),
                digits.substring(8, 12),
                digits.substring(12, 14));
    }

    public static String removerFormatacao(String cnpj) {
        if (cnpj == null) {
            return null;
        }
        return cnpj.replaceAll("[^0-9]", "");
    }

    private static int calcularDigito(String cnpj, int[] pesos, int tamanho) {
        int soma = 0;
        for (int i = 0; i < tamanho; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}