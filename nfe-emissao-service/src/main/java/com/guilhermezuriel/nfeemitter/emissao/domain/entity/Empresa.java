package com.guilhermezuriel.nfeemitter.emissao.domain.entity;

import com.guilhermezuriel.nfeemitter.emissao.domain.enums.RegimeTributario;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.Cnpj;

import java.time.LocalDateTime;
import java.util.UUID;

public class Empresa {

    private UUID id;
    private String razaoSocial;
    private String nomeFantasia;
    private Cnpj cnpj;
    private String inscricaoEstadual;
    private String endereco;
    private String municipio;
    private String uf;
    private String cep;
    private String telefone;
    private RegimeTributario regimeTributario;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Empresa() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public Cnpj getCnpj() { return cnpj; }
    public void setCnpj(Cnpj cnpj) { this.cnpj = cnpj; }

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

    public RegimeTributario getRegimeTributario() { return regimeTributario; }
    public void setRegimeTributario(RegimeTributario regimeTributario) { this.regimeTributario = regimeTributario; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
