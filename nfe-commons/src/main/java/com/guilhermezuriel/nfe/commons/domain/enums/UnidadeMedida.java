package com.guilhermezuriel.nfe.commons.domain.enums;

/**
 * Unidades de medida comercial aceitas na NF-e.
 */
public enum UnidadeMedida {

    UN("Unidade"),
    KG("Quilograma"),
    LT("Litro"),
    MT("Metro"),
    M2("Metro quadrado"),
    M3("Metro cúbico"),
    CX("Caixa"),
    PC("Peça"),
    PCT("Pacote"),
    DZ("Dúzia"),
    TON("Tonelada");

    private final String descricao;

    UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}