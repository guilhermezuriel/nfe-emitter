CREATE SEQUENCE nfe_emitter.nfe_numero_seq
    START WITH 1
    INCREMENT BY 1
    NO CYCLE;

COMMENT ON SEQUENCE nfe_emitter.nfe_numero_seq IS 'Numeração sequencial das NF-e emitidas';