CREATE TABLE nfe_item (
                          id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          nfe_id          UUID         NOT NULL,
                          numero_item     INTEGER      NOT NULL,
                          codigo_produto  VARCHAR(60)  NOT NULL,
                          descricao       VARCHAR(120) NOT NULL,
                          ncm             CHAR(8)      NOT NULL,
                          cfop            CHAR(4)      NOT NULL,
                          unidade         VARCHAR(10)  NOT NULL,
                          quantidade      DECIMAL(15,4) NOT NULL,
                          valor_unitario  DECIMAL(15,4) NOT NULL,
                          valor_total     DECIMAL(15,2) NOT NULL,
                          created_at      TIMESTAMP    NOT NULL DEFAULT now(),

                          CONSTRAINT fk_nfe_item_nfe    FOREIGN KEY (nfe_id) REFERENCES nfe(id) ON DELETE CASCADE,
                          CONSTRAINT uk_nfe_item_numero UNIQUE (nfe_id, numero_item),
                          CONSTRAINT ck_nfe_item_qtd    CHECK (quantidade > 0),
                          CONSTRAINT ck_nfe_item_valor  CHECK (valor_unitario > 0)
);

CREATE INDEX idx_nfe_item_nfe ON nfe_item (nfe_id);

COMMENT ON TABLE nfe_item IS 'Itens/produtos de cada NF-e (1 a 990 itens por nota)';
COMMENT ON COLUMN nfe_item.ncm IS 'Nomenclatura Comum do Mercosul — 8 dígitos, define alíquota IPI';
COMMENT ON COLUMN nfe_item.cfop IS 'Código Fiscal de Operações e Prestações — 4 dígitos';