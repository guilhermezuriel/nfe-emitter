CREATE TABLE nfe_emitter.empresa (
                         id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         razao_social    VARCHAR(200) NOT NULL,
                         nome_fantasia   VARCHAR(60),
                         cnpj            VARCHAR(14)     NOT NULL,
                         inscricao_estadual VARCHAR(20) NOT NULL,
                         endereco        VARCHAR(300) NOT NULL,
                         municipio       VARCHAR(100) NOT NULL,
                         uf              VARCHAR(2)      NOT NULL,
                         cep             VARCHAR(8),
                         telefone        VARCHAR(20),
                         regime_tributario VARCHAR(30) NOT NULL DEFAULT 'LUCRO_PRESUMIDO',
                         created_at      TIMESTAMP    NOT NULL DEFAULT now(),
                         updated_at      TIMESTAMP    NOT NULL DEFAULT now(),

                         CONSTRAINT uk_empresa_cnpj UNIQUE (cnpj),
                         CONSTRAINT ck_empresa_uf   CHECK (LENGTH(uf) = 2)
);

CREATE INDEX idx_empresa_cnpj ON nfe_emitter.empresa (cnpj);
CREATE INDEX idx_empresa_uf   ON nfe_emitter.empresa (uf);

COMMENT ON TABLE nfe_emitter.empresa IS 'Cadastro de empresas participantes das NF-e';
COMMENT ON COLUMN nfe_emitter.empresa.regime_tributario IS 'SIMPLES_NACIONAL, LUCRO_PRESUMIDO ou LUCRO_REAL';