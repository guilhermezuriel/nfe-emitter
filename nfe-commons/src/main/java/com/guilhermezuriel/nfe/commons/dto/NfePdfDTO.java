package com.guilhermezuriel.nfe.commons.dto;

import java.time.LocalDateTime;
import java.util.UUID;


public record NfePdfDTO(

        UUID id,

        UUID nfeId,

        String storageKey,

        String bucket,

        Long fileSize,

        LocalDateTime geradoEm

) {

    public String getDownloadUrl(String minioEndpoint) {
        return String.format("%s/%s/%s", minioEndpoint, bucket, storageKey);
    }
}