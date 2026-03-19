package com.guilhermezuriel.nfe.commons.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;


public record NfeEvent<T>(

        UUID eventId,

        String eventType,

        String source,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,

        T payload

) {

    public static <T> NfeEvent<T> of(String eventType, String source, T payload) {
        return new NfeEvent<>(
                UUID.randomUUID(),
                eventType,
                source,
                LocalDateTime.now(),
                payload
        );
    }
}