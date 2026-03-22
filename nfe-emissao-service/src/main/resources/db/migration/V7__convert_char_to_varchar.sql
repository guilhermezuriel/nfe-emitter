-- Converte colunas CHAR para VARCHAR para compatibilidade com mapeamento JPA

ALTER TABLE nfe_emitter.nfe
    ALTER COLUMN chave_acesso TYPE VARCHAR(44);

ALTER TABLE nfe_emitter.empresa
    ALTER COLUMN cnpj TYPE VARCHAR(14),
    ALTER COLUMN uf   TYPE VARCHAR(2),
    ALTER COLUMN cep  TYPE VARCHAR(8);

ALTER TABLE nfe_emitter.nfe_item
    ALTER COLUMN ncm  TYPE VARCHAR(8),
    ALTER COLUMN cfop TYPE VARCHAR(4);
