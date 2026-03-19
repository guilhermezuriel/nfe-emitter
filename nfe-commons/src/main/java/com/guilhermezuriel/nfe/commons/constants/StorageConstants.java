package com.guilhermezuriel.nfe.commons.constants;

/**
 * Constantes de armazenamento (MinIO/S3).
 */
public final class StorageConstants {

    private StorageConstants() {}

    public static final String BUCKET_NFE_PDFS = "nfe-pdfs";

    /** Padrão de chave: ano/mês/nfe-{chaveAcesso}.pdf */
    public static final String STORAGE_KEY_PATTERN = "%d/%02d/nfe-%s.pdf";

    public static final String CONTENT_TYPE_PDF = "application/pdf";
}