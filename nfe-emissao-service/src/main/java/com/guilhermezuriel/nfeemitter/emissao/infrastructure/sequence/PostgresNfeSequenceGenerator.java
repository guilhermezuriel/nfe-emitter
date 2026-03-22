package com.guilhermezuriel.nfeemitter.emissao.infrastructure.sequence;

import com.guilhermezuriel.nfeemitter.emissao.application.port.out.NfeSequenceGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostgresNfeSequenceGenerator implements NfeSequenceGenerator {

    private final JdbcTemplate jdbcTemplate;

    public PostgresNfeSequenceGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long proximo() {
        Long valor = jdbcTemplate.queryForObject("SELECT nextval('nfe_numero_seq')", Long.class);
        if (valor == null) {
            throw new IllegalStateException("Falha ao gerar número sequencial para NF-e");
        }
        return valor;
    }
}
