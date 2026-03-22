CREATE TABLE nfe_emitter.nfe_pdf (
                         id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         nfe_id      UUID         NOT NULL,
                         storage_key VARCHAR(200) NOT NULL,
                         bucket      VARCHAR(100) NOT NULL DEFAULT 'nfe-pdfs',
                         file_size   BIGINT       NOT NULL DEFAULT 0,
                         gerado_em   TIMESTAMP    NOT NULL DEFAULT now(),

                         CONSTRAINT fk_nfe_pdf_nfe FOREIGN KEY (nfe_id) REFERENCES nfe_emitter.nfe(id) ON DELETE CASCADE,
                         CONSTRAINT uk_nfe_pdf_nfe UNIQUE (nfe_id)
);

CREATE INDEX idx_nfe_pdf_nfe ON nfe_emitter.nfe_pdf (nfe_id);

COMMENT ON TABLE nfe_emitter.nfe_pdf IS 'Referência ao DANFE armazenado';
COMMENT ON COLUMN nfe_emitter.nfe_pdf.storage_key IS 'Caminho no bucket: ano/mes/nfe-{chaveAcesso}.pdf';