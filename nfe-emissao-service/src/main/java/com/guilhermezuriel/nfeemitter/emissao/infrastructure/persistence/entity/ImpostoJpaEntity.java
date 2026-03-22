package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "imposto", schema = "nfe_emitter")
public class ImpostoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nfe_item_id", nullable = false, unique = true)
    private NfeItemJpaEntity nfeItem;

    @Column(name = "icms_base", nullable = false)
    private BigDecimal icmsBase = BigDecimal.ZERO;

    @Column(name = "icms_aliquota", nullable = false)
    private BigDecimal icmsAliquota = BigDecimal.ZERO;

    @Column(name = "icms_valor", nullable = false)
    private BigDecimal icmsValor = BigDecimal.ZERO;

    @Column(name = "pis_base", nullable = false)
    private BigDecimal pisBase = BigDecimal.ZERO;

    @Column(name = "pis_aliquota", nullable = false)
    private BigDecimal pisAliquota = BigDecimal.ZERO;

    @Column(name = "pis_valor", nullable = false)
    private BigDecimal pisValor = BigDecimal.ZERO;

    @Column(name = "cofins_base", nullable = false)
    private BigDecimal cofinsBase = BigDecimal.ZERO;

    @Column(name = "cofins_aliquota", nullable = false)
    private BigDecimal cofinsAliquota = BigDecimal.ZERO;

    @Column(name = "cofins_valor", nullable = false)
    private BigDecimal cofinsValor = BigDecimal.ZERO;

    @Column(name = "ipi_base", nullable = false)
    private BigDecimal ipiBase = BigDecimal.ZERO;

    @Column(name = "ipi_aliquota", nullable = false)
    private BigDecimal ipiAliquota = BigDecimal.ZERO;

    @Column(name = "ipi_valor", nullable = false)
    private BigDecimal ipiValor = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public NfeItemJpaEntity getNfeItem() { return nfeItem; }
    public void setNfeItem(NfeItemJpaEntity nfeItem) { this.nfeItem = nfeItem; }

    public BigDecimal getIcmsBase() { return icmsBase; }
    public void setIcmsBase(BigDecimal icmsBase) { this.icmsBase = icmsBase; }

    public BigDecimal getIcmsAliquota() { return icmsAliquota; }
    public void setIcmsAliquota(BigDecimal icmsAliquota) { this.icmsAliquota = icmsAliquota; }

    public BigDecimal getIcmsValor() { return icmsValor; }
    public void setIcmsValor(BigDecimal icmsValor) { this.icmsValor = icmsValor; }

    public BigDecimal getPisBase() { return pisBase; }
    public void setPisBase(BigDecimal pisBase) { this.pisBase = pisBase; }

    public BigDecimal getPisAliquota() { return pisAliquota; }
    public void setPisAliquota(BigDecimal pisAliquota) { this.pisAliquota = pisAliquota; }

    public BigDecimal getPisValor() { return pisValor; }
    public void setPisValor(BigDecimal pisValor) { this.pisValor = pisValor; }

    public BigDecimal getCofinsBase() { return cofinsBase; }
    public void setCofinsBase(BigDecimal cofinsBase) { this.cofinsBase = cofinsBase; }

    public BigDecimal getCofinsAliquota() { return cofinsAliquota; }
    public void setCofinsAliquota(BigDecimal cofinsAliquota) { this.cofinsAliquota = cofinsAliquota; }

    public BigDecimal getCofinsValor() { return cofinsValor; }
    public void setCofinsValor(BigDecimal cofinsValor) { this.cofinsValor = cofinsValor; }

    public BigDecimal getIpiBase() { return ipiBase; }
    public void setIpiBase(BigDecimal ipiBase) { this.ipiBase = ipiBase; }

    public BigDecimal getIpiAliquota() { return ipiAliquota; }
    public void setIpiAliquota(BigDecimal ipiAliquota) { this.ipiAliquota = ipiAliquota; }

    public BigDecimal getIpiValor() { return ipiValor; }
    public void setIpiValor(BigDecimal ipiValor) { this.ipiValor = ipiValor; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
