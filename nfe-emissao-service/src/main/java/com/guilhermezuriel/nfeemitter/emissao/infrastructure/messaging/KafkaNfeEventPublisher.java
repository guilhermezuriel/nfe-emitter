package com.guilhermezuriel.nfeemitter.emissao.infrastructure.messaging;

import com.guilhermezuriel.nfe.commons.constants.KafkaTopics;
import com.guilhermezuriel.nfe.commons.event.NfeEmitidaPayload;
import com.guilhermezuriel.nfe.commons.event.NfeEvent;
import com.guilhermezuriel.nfeemitter.emissao.application.port.out.NfeEventPublisher;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaNfeEventPublisher implements NfeEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaNfeEventPublisher.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaNfeEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publicarNfeEmitida(Nfe nfe) {
        NfeEmitidaPayload payload = new NfeEmitidaPayload(
                nfe.getId(),
                nfe.getNumero(),
                nfe.getSerie(),
                nfe.getChaveAcesso().valor(),
                nfe.getEmitente().getCnpj().valor(),
                nfe.getDestinatario().getCnpj().valor(),
                nfe.getEmitente().getUf(),
                nfe.getDestinatario().getUf(),
                com.guilhermezuriel.nfe.commons.domain.enums.NaturezaOperacao.valueOf(
                        nfe.getNaturezaOperacao().name()),
                nfe.getValorTotalProdutos(),
                nfe.getItens().size(),
                nfe.getDataEmissao()
        );

        NfeEvent<NfeEmitidaPayload> event = NfeEvent.of("NfeEmitida", "nfe-emissao-service", payload);

        kafkaTemplate.send(KafkaTopics.NFE_EMITIDA, nfe.getChaveAcesso().valor(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Falha ao publicar evento NfeEmitida para NF-e {}: {}",
                                nfe.getId(), ex.getMessage());
                    } else {
                        log.info("Evento NfeEmitida publicado: nfeId={}, chave={}",
                                nfe.getId(), nfe.getChaveAcesso().valor());
                    }
                });
    }
}
