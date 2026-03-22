package com.guilhermezuriel.nfeemitter.emissao.application.port.out;

import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;

public interface NfeEventPublisher {

    void publicarNfeEmitida(Nfe nfe);
}
