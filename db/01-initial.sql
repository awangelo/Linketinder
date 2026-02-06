CREATE TABLE candidato (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    pais VARCHAR(50) NOT NULL,
    estado VARCHAR(2),
    cep VARCHAR(9) NOT NULL,
    descricao TEXT,
    senha VARCHAR(255) NOT NULL, -- Ver outros tipos depois
    telefone VARCHAR(11),
    linkedin VARCHAR(255)
);

CREATE TABLE empresa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    descricao TEXT,
    pais VARCHAR(50) NOT NULL,
    estado VARCHAR(2),
    cep VARCHAR(9) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE competencia (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE candidato_competencia (
    candidato_id INTEGER REFERENCES candidato(id) ON DELETE CASCADE,
    competencia_id INTEGER REFERENCES competencia(id) ON DELETE CASCADE,
    PRIMARY KEY (candidato_id, competencia_id)
);

CREATE TABLE vaga (
    id SERIAL PRIMARY KEY,
    empresa_id INTEGER NOT NULL REFERENCES empresa(id) ON DELETE CASCADE,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    local_vaga VARCHAR(100) NOT NULL
);

CREATE TABLE vaga_competencia (
    vaga_id INTEGER REFERENCES vaga(id) ON DELETE CASCADE,
    competencia_id INTEGER REFERENCES competencia(id) ON DELETE CASCADE,
    PRIMARY KEY (vaga_id, competencia_id)
);

CREATE TABLE curtida (
    id SERIAL PRIMARY KEY,
    candidato_id INTEGER REFERENCES candidato(id) ON DELETE CASCADE,
    vaga_id INTEGER REFERENCES vaga(id) ON DELETE CASCADE,
    origem_curtida VARCHAR(10) NOT NULL CHECK (origem_curtida IN ('CANDIDATO', 'EMPRESA')), -- Quem deu o like
    UNIQUE(candidato_id, vaga_id, origem_curtida) -- Apenas um like para cada
);