package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nfe", schema = "nfe_emitter")
public class NfeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero", nullable = false)
    private Long numero;

    @Column(name = "serie", nullable = false)
    private Integer serie;

    @Column(name = "chave_acesso", nullable = false, unique = true, length = 44)
    private String chaveAcesso;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @Column(name = "natureza_operacao", nullable = false, length = 30)
    private String naturezaOperacao;

    @Column(name = "valor_total_produtos", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotalProdutos = BigDecimal.ZERO;

    @Column(name = "valor_total_impostos", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotalImpostos = BigDecimal.ZERO;

    @Column(name = "valor_total_nfe", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotalNfe = BigDecimal.ZERO;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emitente_id", nullable = false)
    private EmpresaJpaEntity emitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private EmpresaJpaEntity destinatario;

    @OneToMany(mappedBy = "nfe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("numeroItem ASC")
    private List<NfeItemJpaEntity> itens = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Long getNumero() { return numero; }
    public void setNumero(Long numero) { this.numero = numero; }

    public Integer getSerie() { return serie; }
    public void setSerie(Integer serie) { this.serie = serie; }

    public String getChaveAcesso() { return chaveAcesso; }
    public void setChaveAcesso(String chaveAcesso) { this.chaveAcesso = chaveAcesso; }

    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDateTime dataEmissao) { this.dataEmissao = dataEmissao; }

    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }

    public String getNaturezaOperacao() { return naturezaOperacao; }
    public void setNaturezaOperacao(String naturezaOperacao) { this.naturezaOperacao = naturezaOperacao; }

    public BigDecimal getValorTotalProdutos() { return valorTotalProdutos; }
    public void setValorTotalProdutos(BigDecimal valorTotalProdutos) { this.valorTotalProdutos = valorTotalProdutos; }

    public BigDecimal getValorTotalImpostos() { return valorTotalImpostos; }
    public void setValorTotalImpostos(BigDecimal valorTotalImpostos) { this.valorTotalImpostos = valorTotalImpostos; }

    public BigDecimal getValorTotalNfe() { return valorTotalNfe; }
    public void setValorTotalNfe(BigDecimal valorTotalNfe) { this.valorTotalNfe = valorTotalNfe; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public EmpresaJpaEntity getEmitente() { return emitente; }
    public void setEmitente(EmpresaJpaEntity emitente) { this.emitente = emitente; }

    public EmpresaJpaEntity getDestinatario() { return destinatario; }
    public void setDestinatario(EmpresaJpaEntity destinatario) { this.destinatario = destinatario; }

    public List<NfeItemJpaEntity> getItens() { return itens; }
    public void setItens(List<NfeItemJpaEntity> itens) { this.itens = itens; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
