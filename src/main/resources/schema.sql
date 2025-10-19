CREATE TABLE IF NOT EXISTS perfil (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  senha VARCHAR(255) NOT NULL,
  perfil_id INT REFERENCES perfil(id)
);

CREATE TABLE IF NOT EXISTS status (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT NULL UNIQUE,
  descricao VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS roteiro (
  id SERIAL PRIMARY KEY,
  titulo VARCHAR(255),
  texto TEXT NOT NULL,
  data_envio TIMESTAMP DEFAULT NOW(),
  usuario_responsavel_id INT REFERENCES usuario(id),
  status_id INT REFERENCES status(id),
  justificativa TEXT,
  data_ultima_atualizacao TIMESTAMP DEFAULT NOW(),
  protocolo VARCHAR(20) UNIQUE,
  nome_autor VARCHAR(100) NOT NULL,
  email_autor VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS voto_aprovador (
  id SERIAL PRIMARY KEY,
  roteiro_id INT REFERENCES roteiro(id) ON DELETE CASCADE,
  aprovador_id INT REFERENCES usuario(id),
  voto BOOLEAN,
  data_voto TIMESTAMP DEFAULT NOW()
);