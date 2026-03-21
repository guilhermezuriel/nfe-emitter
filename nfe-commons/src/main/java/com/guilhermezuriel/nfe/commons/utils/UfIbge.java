package com.guilhermezuriel.nfe.commons.utils;

import java.util.Map;

/**
 * Mapeamento de siglas UF para códigos IBGE.
 * Necessário para compor a chave de acesso da NF-e.
 */
public final class UfIbge {

    private UfIbge() {}

    private static final Map<String, Integer> CODIGOS = Map.ofEntries(
            Map.entry("AC", 12), Map.entry("AL", 27), Map.entry("AM", 13),
            Map.entry("AP", 16), Map.entry("BA", 29), Map.entry("CE", 23),
            Map.entry("DF", 53), Map.entry("ES", 32), Map.entry("GO", 52),
            Map.entry("MA", 21), Map.entry("MG", 31), Map.entry("MS", 50),
            Map.entry("MT", 51), Map.entry("PA", 15), Map.entry("PB", 25),
            Map.entry("PE", 26), Map.entry("PI", 22), Map.entry("PR", 41),
            Map.entry("RJ", 33), Map.entry("RN", 24), Map.entry("RO", 11),
            Map.entry("RR", 14), Map.entry("RS", 43), Map.entry("SC", 42),
            Map.entry("SE", 28), Map.entry("SP", 35), Map.entry("TO", 17)
    );

    /**
     * Retorna o código IBGE da UF.
     *
     * @param uf sigla com 2 caracteres (ex: "SE", "SP")
     * @return código IBGE
     * @throws IllegalArgumentException se a UF não existe
     */
    public static int getCodigo(String uf) {
        Integer codigo = CODIGOS.get(uf.toUpperCase());
        if (codigo == null) {
            throw new IllegalArgumentException("UF inválida: " + uf);
        }
        return codigo;
    }

    /**
     * Verifica se a sigla UF é válida.
     */
    public static boolean isValida(String uf) {
        return uf != null && CODIGOS.containsKey(uf.toUpperCase());
    }
}