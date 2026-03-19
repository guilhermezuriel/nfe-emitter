package com.guilhermezuriel.nfe.commons.domain.enums;

/**
 * Natureza da operação fiscal.
 * Determina o CFOP e o tratamento tributário.
 */
public enum NaturezaOperacao {

    VENDA("Venda de mercadoria", "5102", "6102"),
    DEVOLUCAO("Devolução de mercadoria", "5202", "6202"),
    TRANSFERENCIA("Transferência de mercadoria", "5152", "6152"),
    REMESSA("Remessa para industrialização", "5901", "6901"),
    BONIFICACAO("Remessa em bonificação", "5910", "6910");

    private final String descricao;
    private final String cfopInterno;
    private final String cfopExterno;

    NaturezaOperacao(String descricao, String cfopInterno, String cfopExterno) {
        this.descricao = descricao;
        this.cfopInterno = cfopInterno;
        this.cfopExterno = cfopExterno;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCfopInterno() {
        return cfopInterno;
    }

    public String getCfopExterno() {
        return cfopExterno;
    }

    /**
     * Retorna o CFOP correto baseado se a operação é interna ou interestadual.
     */
    public String getCfop(boolean operacaoInterna) {
        return operacaoInterna ? cfopInterno : cfopExterno;
    }
}
