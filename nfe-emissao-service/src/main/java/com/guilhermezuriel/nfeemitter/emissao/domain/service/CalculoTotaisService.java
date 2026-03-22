package com.guilhermezuriel.nfeemitter.emissao.domain.service;

import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.NfeItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoTotaisService {

    public void calcular(Nfe nfe) {
        calcularItens(nfe);
        nfe.calcularTotais();
    }

    private void calcularItens(Nfe nfe) {
        int numeroItem = 1;
        for (NfeItem item : nfe.getItens()) {
            item.setNumeroItem(numeroItem++);
            BigDecimal valorTotal = item.getQuantidade()
                    .multiply(item.getValorUnitario())
                    .setScale(2, RoundingMode.HALF_UP);
            item.setValorTotal(valorTotal);
        }
    }
}
