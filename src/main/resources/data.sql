-- Perfis
INSERT INTO perfil (id, nome) VALUES
(1, 'ANALISTA'),
(2, 'REVISOR'),
(3, 'APROVADOR')
ON CONFLICT (id) DO NOTHING;

-- Status
INSERT INTO status (id, nome, descricao) VALUES
(1, 'aguardando_analise', 'Aguardando análise'),
(2, 'em_analise', 'Em análise'),
(3, 'aguardando_revisao', 'Aguardando revisão'),
(4, 'em_revisao', 'Em revisão'),
(5, 'aguardando_aprovacao', 'Aguardando aprovação'),
(6, 'em_aprovacao', 'Em aprovação'),
(7, 'aprovado', 'Aprovado'),
(8, 'recusado', 'Recusado')
ON CONFLICT (id) DO NOTHING;

-- Usuários iniciais
INSERT INTO usuario (nome, email, senha, perfil_id) VALUES
('Analista 1', 'analista@cooperfilme.com', '$2a$12$hKJmgTlvzs26PH/4etdMeOa1CeTM5/e.aYFNcGkIFxzq00Oqjamri', 1),
('Revisor 1', 'revisor@cooperfilme.com', '$2a$12$jrgQDAdHNUEdffxD34jRLO8wLcJL60RLXT6J/20SK.6J/5tybT7hG', 2),
('Aprovador 1', 'aprovador1@cooperfilme.com', '$2a$12$D.9RtLnbmXwwoQvKMy1hmutwr7yoEDPi4BV/af1KGOKgMkSlnt4oG', 3),
('Aprovador 2', 'aprovador2@cooperfilme.com', '$2a$12$wmrCD/KARMn92k6It.yR1ezzfSRI.m2BsTKgbfvG4lLhEdc62ZGbm', 3),
('Aprovador 3', 'aprovador3@cooperfilme.com', '$2a$12$jaFyenPSqxUvVY3Jra3Q4e3tSRohmCxL6LveI6gX8V4LqRrBOlYcu', 3)
ON CONFLICT (email) DO NOTHING;
