package com.guilhermezuriel.nfeemitter.emissao.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class NfeItem {

    private UUID id;
    private Integer numeroItem;
    private String codigoProduto;
    private String descricao;
    private String ncm;
    private String cfop;
    private String unidade;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private LocalDateTime createdAt;

    public NfeItem() {}

    public BigDecimal calcularValorTotal() {
        return quantidade.multiply(valorUnitario);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Integer getNumeroItem() { return numeroItem; }
    public void setNumeroItem(Integer numeroItem) { this.numeroItem = numeroItem; }

    public String getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(String codigoProduto) { this.codigoProduto = codigoProduto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getNcm() { return ncm; }
    public void setNcm(String ncm) { this.ncm = ncm; }

    public String getCfop() { return cfop; }
    public void setCfop(String cfop) { this.cfop = cfop; }

    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
