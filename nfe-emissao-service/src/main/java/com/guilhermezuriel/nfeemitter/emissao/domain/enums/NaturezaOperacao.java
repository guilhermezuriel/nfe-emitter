package com.guilhermezuriel.nfeemitter.emissao.domain.enums;

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

    public String getCfop(boolean operacaoInterna) {
        return operacaoInterna ? cfopInterno : cfopExterno;
    }
}
