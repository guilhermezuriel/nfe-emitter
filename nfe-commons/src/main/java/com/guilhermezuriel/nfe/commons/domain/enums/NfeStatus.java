package com.guilhermezuriel.nfe.commons.domain.enums;

public enum NfeStatus {

    PENDENTE("Aguardando processamento batch"),
    PROCESSADA("Impostos calculados com sucesso"),
    PDF_GERADO("DANFE gerado e armazenado"),
    CANCELADA("Nota fiscal cancelada"),
    ERRO("Falha no processamento");

    private final String descricao;

    NfeStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean canChangeTo(NfeStatus novoStatus) {
        return switch (this) {
            case PENDENTE -> novoStatus == PROCESSADA || novoStatus == ERRO;
            case PROCESSADA -> novoStatus == PDF_GERADO || novoStatus == CANCELADA;
            case PDF_GERADO -> novoStatus == CANCELADA;
            case ERRO -> novoStatus == PENDENTE;
            case CANCELADA -> false;
        };
    }
}
