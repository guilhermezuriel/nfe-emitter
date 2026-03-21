package com.guilhermezuriel.nfe.commons.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CnpjValidatorTest {

    @Test
    @DisplayName("Deve validar CNPJ correto sem formatação")
    void deveValidarCnpjCorreto() {
        // CNPJ válido: 11.222.333/0001-81
        assertThat(CnpjValidator.isValido("11222333000181")).isTrue();
    }

    @Test
    @DisplayName("Deve validar CNPJ correto com formatação")
    void deveValidarCnpjComFormatacao() {
        assertThat(CnpjValidator.isValido("11.222.333/0001-81")).isTrue();
    }

    @Test
    @DisplayName("Deve rejeitar CNPJ com dígito verificador errado")
    void deveRejeitarCnpjComDigitoErrado() {
        assertThat(CnpjValidator.isValido("11222333000182")).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "00000000000000", "11111111111111", "22222222222222",
            "33333333333333", "99999999999999"
    })
    @DisplayName("Deve rejeitar CNPJ com todos os dígitos iguais")
    void deveRejeitarDigitosIguais(String cnpj) {
        assertThat(CnpjValidator.isValido(cnpj)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123", "1234567890", "123456789012345"})
    @DisplayName("Deve rejeitar CNPJ com tamanho inválido")
    void deveRejeitarTamanhoInvalido(String cnpj) {
        assertThat(CnpjValidator.isValido(cnpj)).isFalse();
    }

    @Test
    @DisplayName("Deve formatar CNPJ corretamente")
    void deveFormatarCnpj() {
        assertThat(CnpjValidator.formatar("11222333000181"))
                .isEqualTo("11.222.333/0001-81");
    }

    @Test
    @DisplayName("Deve remover formatação do CNPJ")
    void deveRemoverFormatacao() {
        assertThat(CnpjValidator.removerFormatacao("11.222.333/0001-81"))
                .isEqualTo("11222333000181");
    }

    @Test
    @DisplayName("Deve lançar exceção ao formatar CNPJ com tamanho errado")
    void deveLancarExcecaoFormatarInvalido() {
        assertThatThrownBy(() -> CnpjValidator.formatar("123"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}