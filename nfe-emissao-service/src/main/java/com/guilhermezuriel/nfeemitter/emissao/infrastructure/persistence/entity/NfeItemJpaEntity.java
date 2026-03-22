package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "nfe_item", schema = "nfe_emitter")
public class NfeItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nfe_id", nullable = false)
    private NfeJpaEntity nfe;

    @Column(name = "numero_item", nullable = false)
    private Integer numeroItem;

    @Column(name = "codigo_produto", nullable = false, length = 60)
    private String codigoProduto;

    @Column(name = "descricao", nullable = false, length = 120)
    private String descricao;

    @Column(name = "ncm", nullable = false, columnDefinition = "CHAR(8)")
    private String ncm;

    @Column(name = "cfop", nullable = false, columnDefinition = "CHAR(4)")
    private String cfop;

    @Column(name = "unidade", nullable = false, length = 10)
    private String unidade;

    @Column(name = "quantidade", nullable = false, precision = 15, scale = 4)
    private BigDecimal quantidade;

    @Column(name = "valor_unitario", nullable = false, precision = 15, scale = 4)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public NfeJpaEntity getNfe() { return nfe; }
    public void setNfe(NfeJpaEntity nfe) { this.nfe = nfe; }

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
