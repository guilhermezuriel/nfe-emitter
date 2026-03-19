package com.guilhermezuriel.nfe.commons.exceptions;


import java.util.List;

/**
 * Exceção lançada quando a validação de uma NF-e falha.
 * Carrega a lista de erros encontrados.
 */
public class NfeValidationException extends NfeBusinessException {

    private final List<String> erros;

    public NfeValidationException(List<String> erros) {
        super("Validação da NF-e falhou: " + String.join("; ", erros));
        this.erros = List.copyOf(erros);
    }

    public NfeValidationException(String erro) {
        this(List.of(erro));
    }

    public List<String> getErros() {
        return erros;
    }
}