CREATE TABLE empresa (
                         id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         razao_social    VARCHAR(200) NOT NULL,
                         nome_fantasia   VARCHAR(60),
                         cnpj            CHAR(14)     NOT NULL,
                         inscricao_estadual VARCHAR(20) NOT NULL,
                         endereco        VARCHAR(300) NOT NULL,
                         municipio       VARCHAR(100) NOT NULL,
                         uf              CHAR(2)      NOT NULL,
                         cep             CHAR(8),
                         telefone        VARCHAR(20),
                         regime_tributario VARCHAR(30) NOT NULL DEFAULT 'LUCRO_PRESUMIDO',
                         created_at      TIMESTAMP    NOT NULL DEFAULT now(),
                         updated_at      TIMESTAMP    NOT NULL DEFAULT now(),

                         CONSTRAINT uk_empresa_cnpj UNIQUE (cnpj),
                         CONSTRAINT ck_empresa_uf   CHECK (LENGTH(uf) = 2)
);

CREATE INDEX idx_empresa_cnpj ON empresa (cnpj);
CREATE INDEX idx_empresa_uf   ON empresa (uf);

COMMENT ON TABLE empresa IS 'Cadastro de empresas participantes das NF-e';
COMMENT ON COLUMN empresa.regime_tributario IS 'SIMPLES_NACIONAL, LUCRO_PRESUMIDO ou LUCRO_REAL';