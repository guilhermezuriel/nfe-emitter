package com.guilhermezuriel.nfe.commons.constants;


public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String NFE_EMITIDA = "nfe.emitida";
    public static final String NFE_PROCESSADA = "nfe.processada";
    public static final String NFE_EMITIDA_DLQ = "nfe.emitida.dlq";

    /** Consumer group do Spring Batch */
    public static final String GROUP_BATCH_PROCESSOR = "nfe-batch-processor";

    /** Consumer group do serviço de PDF */
    public static final String GROUP_PDF_SERVICE = "nfe-pdf-service";
}