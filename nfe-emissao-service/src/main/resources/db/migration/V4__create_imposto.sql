CREATE TABLE imposto (
                         id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         nfe_item_id     UUID          NOT NULL,

    -- ICMS
                         icms_base       DECIMAL(15,2) NOT NULL DEFAULT 0,
                         icms_aliquota   DECIMAL(5,2)  NOT NULL DEFAULT 0,
                         icms_valor      DECIMAL(15,2) NOT NULL DEFAULT 0,

    -- PIS
                         pis_base        DECIMAL(15,2) NOT NULL DEFAULT 0,
                         pis_aliquota    DECIMAL(5,2)  NOT NULL DEFAULT 0,
                         pis_valor       DECIMAL(15,2) NOT NULL DEFAULT 0,

    -- COFINS
                         cofins_base     DECIMAL(15,2) NOT NULL DEFAULT 0,
                         cofins_aliquota DECIMAL(5,2)  NOT NULL DEFAULT 0,
                         cofins_valor    DECIMAL(15,2) NOT NULL DEFAULT 0,

    -- IPI
                         ipi_base        DECIMAL(15,2) NOT NULL DEFAULT 0,
                         ipi_aliquota    DECIMAL(5,2)  NOT NULL DEFAULT 0,
                         ipi_valor       DECIMAL(15,2) NOT NULL DEFAULT 0,

                         created_at      TIMESTAMP     NOT NULL DEFAULT now(),

                         CONSTRAINT fk_imposto_item FOREIGN KEY (nfe_item_id) REFERENCES nfe_item(id) ON DELETE CASCADE,
                         CONSTRAINT uk_imposto_item UNIQUE (nfe_item_id)
);

CREATE INDEX idx_imposto_item ON imposto (nfe_item_id);

COMMENT ON TABLE imposto IS 'Impostos calculados pelo Spring Batch para cada item da NF-e';
COMMENT ON COLUMN imposto.icms_aliquota IS 'Percentual: 18% interno, 7% ou 12% interestadual';