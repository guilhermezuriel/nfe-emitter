package com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.mapper;

import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Empresa;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.NfeItem;
import com.guilhermezuriel.nfeemitter.emissao.domain.enums.NaturezaOperacao;
import com.guilhermezuriel.nfeemitter.emissao.domain.enums.RegimeTributario;
import com.guilhermezuriel.nfeemitter.emissao.domain.enums.StatusNfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.ChaveAcesso;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.Cnpj;
import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity.EmpresaJpaEntity;
import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity.NfeItemJpaEntity;
import com.guilhermezuriel.nfeemitter.emissao.infrastructure.persistence.entity.NfeJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NfePersistenceMapper {

    public NfeJpaEntity toJpaEntity(Nfe nfe) {
        NfeJpaEntity entity = new NfeJpaEntity();
        entity.setId(nfe.getId());
        entity.setNumero(nfe.getNumero());
        entity.setSerie(nfe.getSerie());
        entity.setChaveAcesso(nfe.getChaveAcesso().valor());
        entity.setDataEmissao(nfe.getDataEmissao());
        entity.setDataProcessamento(nfe.getDataProcessamento());
        entity.setNaturezaOperacao(nfe.getNaturezaOperacao().name());
        entity.setStatus(nfe.getStatus().name());
        entity.setValorTotalProdutos(nfe.getValorTotalProdutos());
        entity.setValorTotalImpostos(nfe.getValorTotalImpostos());
        entity.setValorTotalNfe(nfe.getValorTotalNfe());
        entity.setEmitente(toJpaEntity(nfe.getEmitente()));
        entity.setDestinatario(toJpaEntity(nfe.getDestinatario()));

        List<NfeItemJpaEntity> itens = nfe.getItens().stream()
                .map(item -> toItemJpaEntity(item, entity))
                .toList();
        entity.setItens(itens);

        return entity;
    }

    public Nfe toDomain(NfeJpaEntity entity) {
        Nfe nfe = new Nfe();
        nfe.setId(entity.getId());
        nfe.setNumero(entity.getNumero());
        nfe.setSerie(entity.getSerie());
        nfe.setChaveAcesso(new ChaveAcesso(entity.getChaveAcesso()));
        nfe.setDataEmissao(entity.getDataEmissao());
        nfe.setDataProcessamento(entity.getDataProcessamento());
        nfe.setNaturezaOperacao(NaturezaOperacao.valueOf(entity.getNaturezaOperacao()));
        nfe.setStatus(StatusNfe.valueOf(entity.getStatus()));
        nfe.setValorTotalProdutos(entity.getValorTotalProdutos());
        nfe.setValorTotalImpostos(entity.getValorTotalImpostos());
        nfe.setValorTotalNfe(entity.getValorTotalNfe());
        nfe.setEmitente(toDomain(entity.getEmitente()));
        nfe.setDestinatario(toDomain(entity.getDestinatario()));
        nfe.setItens(entity.getItens().stream().map(this::toItemDomain).toList());
        nfe.setCreatedAt(entity.getCreatedAt());
        nfe.setUpdatedAt(entity.getUpdatedAt());
        return nfe;
    }

    public EmpresaJpaEntity toJpaEntity(Empresa empresa) {
        EmpresaJpaEntity entity = new EmpresaJpaEntity();
        entity.setId(empresa.getId());
        entity.setRazaoSocial(empresa.getRazaoSocial());
        entity.setNomeFantasia(empresa.getNomeFantasia());
        entity.setCnpj(empresa.getCnpj().valor());
        entity.setInscricaoEstadual(empresa.getInscricaoEstadual());
        entity.setEndereco(empresa.getEndereco());
        entity.setMunicipio(empresa.getMunicipio());
        entity.setUf(empresa.getUf());
        entity.setCep(empresa.getCep());
        entity.setTelefone(empresa.getTelefone());
        entity.setRegimeTributario(empresa.getRegimeTributario().name());
        return entity;
    }

    public Empresa toDomain(EmpresaJpaEntity entity) {
        Empresa empresa = new Empresa();
        empresa.setId(entity.getId());
        empresa.setRazaoSocial(entity.getRazaoSocial());
        empresa.setNomeFantasia(entity.getNomeFantasia());
        empresa.setCnpj(new Cnpj(entity.getCnpj()));
        empresa.setInscricaoEstadual(entity.getInscricaoEstadual());
        empresa.setEndereco(entity.getEndereco());
        empresa.setMunicipio(entity.getMunicipio());
        empresa.setUf(entity.getUf());
        empresa.setCep(entity.getCep());
        empresa.setTelefone(entity.getTelefone());
        empresa.setRegimeTributario(RegimeTributario.valueOf(entity.getRegimeTributario()));
        empresa.setCreatedAt(entity.getCreatedAt());
        empresa.setUpdatedAt(entity.getUpdatedAt());
        return empresa;
    }

    private NfeItemJpaEntity toItemJpaEntity(NfeItem item, NfeJpaEntity nfeEntity) {
        NfeItemJpaEntity entity = new NfeItemJpaEntity();
        entity.setId(item.getId());
        entity.setNfe(nfeEntity);
        entity.setNumeroItem(item.getNumeroItem());
        entity.setCodigoProduto(item.getCodigoProduto());
        entity.setDescricao(item.getDescricao());
        entity.setNcm(item.getNcm());
        entity.setCfop(item.getCfop());
        entity.setUnidade(item.getUnidade());
        entity.setQuantidade(item.getQuantidade());
        entity.setValorUnitario(item.getValorUnitario());
        entity.setValorTotal(item.getValorTotal());
        return entity;
    }

    private NfeItem toItemDomain(NfeItemJpaEntity entity) {
        NfeItem item = new NfeItem();
        item.setId(entity.getId());
        item.setNumeroItem(entity.getNumeroItem());
        item.setCodigoProduto(entity.getCodigoProduto());
        item.setDescricao(entity.getDescricao());
        item.setNcm(entity.getNcm());
        item.setCfop(entity.getCfop());
        item.setUnidade(entity.getUnidade());
        item.setQuantidade(entity.getQuantidade());
        item.setValorUnitario(entity.getValorUnitario());
        item.setValorTotal(entity.getValorTotal());
        item.setCreatedAt(entity.getCreatedAt());
        return item;
    }
}
