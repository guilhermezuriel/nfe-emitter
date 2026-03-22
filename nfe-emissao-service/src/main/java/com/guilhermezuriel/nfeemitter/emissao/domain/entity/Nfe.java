package com.guilhermezuriel.nfeemitter.emissao.domain.entity;

import com.guilhermezuriel.nfeemitter.emissao.domain.enums.NaturezaOperacao;
import com.guilhermezuriel.nfeemitter.emissao.domain.enums.StatusNfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.exception.NfeBusinessException;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.ChaveAcesso;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Nfe {

    private UUID id;
    private Long numero;
    private Integer serie;
    private ChaveAcesso chaveAcesso;
    private LocalDateTime dataEmissao;
    private LocalDateTime dataProcessamento;
    private NaturezaOperacao naturezaOperacao;
    private StatusNfe status;
    private Empresa emitente;
    private Empresa destinatario;
    private List<NfeItem> itens = new ArrayList<>();
    private BigDecimal valorTotalProdutos;
    private BigDecimal valorTotalImpostos;
    private BigDecimal valorTotalNfe;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Nfe() {}

    public void calcularTotais() {
        this.valorTotalProdutos = itens.stream()
                .map(NfeItem::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.valorTotalNfe = this.valorTotalProdutos;
        this.valorTotalImpostos = BigDecimal.ZERO;
    }

    public boolean isOperacaoInterna() {
        return emitente.getUf().equalsIgnoreCase(destinatario.getUf());
    }

    public void validarTransicaoStatus(StatusNfe novoStatus) {
        if (!this.status.podeTransicionarPara(novoStatus)) {
            throw new NfeBusinessException(
                    "Transição de status inválida: " + status + " → " + novoStatus);
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }

    public Integer getSerie() { return serie; }
    public void setSerie(Integer serie) { this.serie = serie; }

    public ChaveAcesso getChaveAcesso() { return chaveAcesso; }
    public void setChaveAcesso(ChaveAcesso chaveAcesso) { this.chaveAcesso = chaveAcesso; }

    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDateTime dataEmissao) { this.dataEmissao = dataEmissao; }

    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }

    public NaturezaOperacao getNaturezaOperacao() { return naturezaOperacao; }
    public void setNaturezaOperacao(NaturezaOperacao naturezaOperacao) { this.naturezaOperacao = naturezaOperacao; }

    public StatusNfe getStatus() { return status; }
    public void setStatus(StatusNfe status) { this.status = status; }

    public Empresa getEmitente() { return emitente; }
    public void setEmitente(Empresa emitente) { this.emitente = emitente; }

    public Empresa getDestinatario() { return destinatario; }
    public void setDestinatario(Empresa destinatario) { this.destinatario = destinatario; }

    public List<NfeItem> getItens() { return itens; }
    public void setItens(List<NfeItem> itens) { this.itens = itens; }

    public BigDecimal getValorTotalProdutos() { return valorTotalProdutos; }
    public void setValorTotalProdutos(BigDecimal valorTotalProdutos) { this.valorTotalProdutos = valorTotalProdutos; }

    public BigDecimal getValorTotalImpostos() { return valorTotalImpostos; }
    public void setValorTotalImpostos(BigDecimal valorTotalImpostos) { this.valorTotalImpostos = valorTotalImpostos; }

    public BigDecimal getValorTotalNfe() { return valorTotalNfe; }
    public void setValorTotalNfe(BigDecimal valorTotalNfe) { this.valorTotalNfe = valorTotalNfe; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
