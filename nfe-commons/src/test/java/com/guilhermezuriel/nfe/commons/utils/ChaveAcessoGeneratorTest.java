package com.guilhermezuriel.nfe.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChaveAcessoGeneratorTest {

    private static final int UF_SERGIPE = 28;
    private static final String CNPJ_VALIDO = "11222333000181";
    private static final LocalDateTime DATA_EMISSAO = LocalDateTime.of(2026, 3, 20, 10, 30);

    @Test
    @DisplayName("Deve gerar chave de acesso com 44 dígitos")
    void deveGerarChaveCom44Digitos() {
        String chave = ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, CNPJ_VALIDO, 1, 1000);

        assertThat(chave).hasSize(44);
        assertThat(chave).matches("\\d{44}");
    }

    @Test
    @DisplayName("Deve conter UF correta nos 2 primeiros dígitos")
    void deveConterUfCorreta() {
        String chave = ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, CNPJ_VALIDO, 1, 1000);

        assertThat(chave.substring(0, 2)).isEqualTo("28");
    }

    @Test
    @DisplayName("Deve conter ano/mês corretos nas posições 3-6")
    void deveConterAnoMesCorretos() {
        String chave = ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, CNPJ_VALIDO, 1, 1000);

        // Março 2026 → "2603"
        assertThat(chave.substring(2, 6)).isEqualTo("2603");
    }

    @Test
    @DisplayName("Deve conter CNPJ do emitente nas posições 7-20")
    void deveConterCnpj() {
        String chave = ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, CNPJ_VALIDO, 1, 1000);

        assertThat(chave.substring(6, 20)).isEqualTo(CNPJ_VALIDO);
    }

    @Test
    @DisplayName("Deve conter modelo 55 (NF-e) nas posições 21-22")
    void deveConterModelo55() {
        String chave = ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, CNPJ_VALIDO, 1, 1000);

        assertThat(chave.substring(20, 22)).isEqualTo("55");
    }

    @Test
    @DisplayName("Chave gerada deve passar na validação")
    void chaveGeradaDeveSerValida() {
        String chave = ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, CNPJ_VALIDO, 1, 1000);

        assertThat(ChaveAcessoGenerator.isValida(chave)).isTrue();
    }

    @Test
    @DisplayName("Deve rejeitar chave com dígito verificador alterado")
    void deveRejeitarChaveAlterada() {
        String chave = ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, CNPJ_VALIDO, 1, 1000);

        // Altera o último dígito
        char ultimo = chave.charAt(43);
        char alterado = ultimo == '0' ? '1' : '0';
        String chaveAlterada = chave.substring(0, 43) + alterado;

        assertThat(ChaveAcessoGenerator.isValida(chaveAlterada)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123", "1234567890123456789012345678901234567890123"})
    @DisplayName("Deve rejeitar chave com tamanho inválido")
    void deveRejeitarTamanhoInvalido(String chave) {
        assertThat(ChaveAcessoGenerator.isValida(chave)).isFalse();
    }

    @Test
    @DisplayName("Deve lançar exceção para CNPJ com tamanho errado")
    void deveLancarExcecaoCnpjInvalido() {
        assertThatThrownBy(() ->
                ChaveAcessoGenerator.gerar(UF_SERGIPE, DATA_EMISSAO, "123", 1, 1000))
                .isInstanceOf(IllegalArgumentException.class);
    }
}