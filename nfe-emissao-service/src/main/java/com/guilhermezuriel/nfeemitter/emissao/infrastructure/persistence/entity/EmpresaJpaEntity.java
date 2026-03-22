package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "empresa", schema = "nfe_emitter")
public class EmpresaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "razao_social", nullable = false, length = 200)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 60)
    private String nomeFantasia;

    @Column(name = "cnpj", nullable = false,  unique = true)
    private String cnpj;

    @Column(name = "inscricao_estadual", nullable = false, length = 20)
    private String inscricaoEstadual;

    @Column(name = "endereco", nullable = false, length = 300)
    private String endereco;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @Column(name = "uf", nullable = false)
    private String uf;

    @Column(name = "cep", columnDefinition = "CHAR(8)")
    private String cep;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "regime_tributario", nullable = false, length = 30)
    private String regimeTributario;

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

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getInscricaoEstadual() { return inscricaoEstadual; }
    public void setInscricaoEstadual(String inscricaoEstadual) { this.inscricaoEstadual = inscricaoEstadual; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getRegimeTributario() { return regimeTributario; }
    public void setRegimeTributario(String regimeTributario) { this.regimeTributario = regimeTributario; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
