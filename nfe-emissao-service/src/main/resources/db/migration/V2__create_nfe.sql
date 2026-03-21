CREATE TABLE nfe (
                     id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                     numero                  BIGINT       NOT NULL,
                     serie                   INTEGER      NOT NULL DEFAULT 1,
                     chave_acesso            CHAR(44)     NOT NULL,
                     data_emissao            TIMESTAMP    NOT NULL,
                     data_processamento      TIMESTAMP,
                     natureza_operacao       VARCHAR(30)  NOT NULL,
                     valor_total_produtos    DECIMAL(15,2) NOT NULL DEFAULT 0,
                     valor_total_impostos    DECIMAL(15,2) NOT NULL DEFAULT 0,
                     valor_total_nfe         DECIMAL(15,2) NOT NULL DEFAULT 0,
                     status                  VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
                     emitente_id             UUID         NOT NULL,
                     destinatario_id         UUID         NOT NULL,
                     created_at              TIMESTAMP    NOT NULL DEFAULT now(),
                     updated_at              TIMESTAMP    NOT NULL DEFAULT now(),

                     CONSTRAINT uk_nfe_chave_acesso  UNIQUE (chave_acesso),
                     CONSTRAINT uk_nfe_numero_serie  UNIQUE (numero, serie),
                     CONSTRAINT fk_nfe_emitente      FOREIGN KEY (emitente_id)      REFERENCES empresa(id),
                     CONSTRAINT fk_nfe_destinatario  FOREIGN KEY (destinatario_id)  REFERENCES empresa(id),
                     CONSTRAINT ck_nfe_status        CHECK (status IN ('PENDENTE','PROCESSADA','PDF_GERADO','CANCELADA','ERRO'))
);

CREATE INDEX idx_nfe_status          ON nfe (status);
CREATE INDEX idx_nfe_emitente        ON nfe (emitente_id);
CREATE INDEX idx_nfe_destinatario    ON nfe (destinatario_id);
CREATE INDEX idx_nfe_data_emissao    ON nfe (data_emissao);
CREATE INDEX idx_nfe_cnpj_status     ON nfe (emitente_id, status);

COMMENT ON TABLE nfe IS 'Notas Fiscais Eletrônicas emitidas no sistema';
COMMENT ON COLUMN nfe.chave_acesso IS 'Chave de 44 dígitos no padrão SEFAZ';
COMMENT ON COLUMN nfe.status IS 'PENDENTE → PROCESSADA → PDF_GERADO | CANCELADA | ERRO';