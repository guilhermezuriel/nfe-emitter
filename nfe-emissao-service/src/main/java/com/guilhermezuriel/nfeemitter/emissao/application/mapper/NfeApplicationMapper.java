package com.guilhermezuriel.nfeemitter.emissao.application.mapper;

import com.guilhermezuriel.nfeemitter.emissao.application.port.in.EmitirNfeCommand;
import com.guilhermezuriel.nfeemitter.emissao.application.port.in.NfeResult;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Empresa;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.Nfe;
import com.guilhermezuriel.nfeemitter.emissao.domain.entity.NfeItem;
import com.guilhermezuriel.nfeemitter.emissao.domain.enums.RegimeTributario;
import com.guilhermezuriel.nfeemitter.emissao.domain.valueobject.Cnpj;

import java.util.List;

public final class NfeApplicationMapper {

    private NfeApplicationMapper() {}

    public static Empresa toEmpresa(EmitirNfeCommand.EmpresaData data) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(new Cnpj(data.cnpj()));
        empresa.setRazaoSocial(data.razaoSocial());
        empresa.setNomeFantasia(data.nomeFantasia());
        empresa.setInscricaoEstadual(data.inscricaoEstadual());
        empresa.setEndereco(data.endereco());
        empresa.setMunicipio(data.municipio());
        empresa.setUf(data.uf());
        empresa.setCep(data.cep());
        empresa.setTelefone(data.telefone());
        empresa.setRegimeTributario(RegimeTributario.valueOf(data.regimeTributario()));
        return empresa;
    }

    public static NfeItem toNfeItem(EmitirNfeCommand.ItemData data) {
        NfeItem item = new NfeItem();
        item.setCodigoProduto(data.codigoProduto());
        item.setDescricao(data.descricao());
        item.setNcm(data.ncm());
        item.setUnidade(data.unidade());
        item.setQuantidade(data.quantidade());
        item.setValorUnitario(data.valorUnitario());
        return item;
    }

    public static List<NfeItem> toNfeItens(List<EmitirNfeCommand.ItemData> itensData) {
        return itensData.stream().map(NfeApplicationMapper::toNfeItem).toList();
    }

    public static NfeResult toResult(Nfe nfe) {
        return new NfeResult(
                nfe.getId(),
                nfe.getNumero(),
                nfe.getSerie(),
                nfe.getChaveAcesso().valor(),
                nfe.getStatus().name(),
                nfe.getNaturezaOperacao().name(),
                nfe.getEmitente().getCnpj().valor(),
                nfe.getDestinatario().getCnpj().valor(),
                nfe.getValorTotalProdutos(),
                nfe.getValorTotalNfe(),
                nfe.getItens().size(),
                nfe.getDataEmissao()
        );
    }
}
