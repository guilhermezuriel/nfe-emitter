package com.guilhermezuriel.nfe.commons.event;

/**
 * Tipos de evento do sistema. Usados no campo eventType do NfeEvent.
 */
public final class EventTypes {

    private EventTypes() {}

    public static final String NFE_EMITIDA = "nfe.emitida";
    public static final String NFE_PROCESSADA = "nfe.processada";
    public static final String NFE_PDF_GERADO = "nfe.pdf.gerado";
    public static final String NFE_CANCELADA = "nfe.cancelada";
    public static final String NFE_ERRO_PROCESSAMENTO = "nfe.erro.processamento";
}