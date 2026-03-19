package com.guilhermezuriel.nfe.commons.exceptions;

/**
 * Exceção base para erros de regra de negócio do domínio NF-e.
 * Todas as exceções de negócio estendem desta.
 */
public class NfeBusinessException extends RuntimeException {

    public NfeBusinessException(String message) {
        super(message);
    }

    public NfeBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}