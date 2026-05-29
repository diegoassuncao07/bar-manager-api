CREATE TABLE produto(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    categoria VARCHAR(100) NOT NULL,
    preco_custo NUMERIC(10, 2),
    preco_venda NUMERIC(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL
);