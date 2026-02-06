INSERT INTO competencia (nome) VALUES 
('JAVA'), ('SPRING_FRAMEWORK'), ('SQL'), ('JAVASCRIPT'), ('REACT'), ('MONGODB'), ('PYTHON'), ('AWS'), ('GROOVY'), ('ANGULAR');

INSERT INTO candidato (nome, sobrenome, data_nascimento, email, cpf, pais, estado, cep, descricao, senha) VALUES
('Alice', 'Liddell', '1997-01-01', 'alice.liddell@mail.com', '12345678901', 'Brasil', 'SP', '01310103', 'Desenvolvedora backend com 3 anos de experiencia', '123456'),
('Joao', 'Pereira', '1993-01-01', 'joao.pereira@mail.com', '23456789012', 'Brasil', 'RJ', '20240420', 'Fullstack developer focado em aplicacoes web', '123456'),
('Felipe', 'Silva', '2000-01-01', 'felipe.silva@mail.com', '34567890123', 'Brasil', 'MG', '30130330', 'Engenheira de dados com conhecimento em cloud', '123456'),
('Matheus', 'Oliveira', '1995-01-01', 'matheus@mail.com', '45678901234', 'Brasil', 'DF', '40010404', 'Desenvolvedor Groovy apaixonado por automacao', '123456'),
('Pedro', 'Santos', '1998-01-01', 'pedro@mail.com', '56789012345', 'Brasil', 'SC', '58050050', 'Frontend developer com foco em UX', '123456');

INSERT INTO candidato_competencia (candidato_id, competencia_id) VALUES
-- Alice (Java, Spring, SQL)
(1, 1), (1, 2), (1, 3),
-- Joao (JS, React, Mongo)
(2, 4), (2, 5), (2, 6),
-- Felipe (Python, SQL, AWS)
(3, 7), (3, 3), (3, 8),
-- Matheus (Groovy, Java, Spring)
(4, 9), (4, 1), (4, 2),
-- Pedro (JS, Angular, React)
(5, 4), (5, 10), (5, 5);

INSERT INTO empresa (nome, email, cnpj, pais, estado, cep, descricao, senha) VALUES
('Arroz Gostoso', 'rh@arrozgostoso.com.br', '12345678000111', 'Brasil', 'SP', '01310100', 'Empresa lider no setor alimenticio', 'empresa123'),
('Imperio do Boliche', 'contato@imperioboliche.com.br', '23456789000222', 'Brasil', 'RJ', '20040020', 'Maior rede de boliches do Brasil', 'empresa123'),
('Brocrosoft', 'rh@brocrosoft.com', '34567890000333', 'Brasil', 'MG', '30130000', 'Consultoria em transformacao digital', 'empresa123'),
('DevMaster', 'sandubinha@devmaster.com', '45678901000444', 'Brasil', 'PR', '80010000', 'Fabrica de software especializada em Groovy', 'empresa123'),
('Caixa', 'contato@caixa.com', '56789012000555', 'Brasil', 'SC', '88010000', 'Especialistas em solucoes bancarias', 'empresa123');

INSERT INTO vaga (empresa_id, nome, descricao, local_vaga) VALUES
(1, 'Desenvolvedor Java Senior', 'Desenvolvimento de APIs REST', 'SP'),
(2, 'Frontend Developer', 'Desenvolvimento de interfaces web', 'RJ'),
(3, 'Engenheiro de Dados', 'Construcao de pipelines de dados', 'MG'),
(4, 'Desenvolvedor Groovy', 'Automacao e desenvolvimento backend', 'PR'),
(5, 'DevOps Engineer', 'Gerenciamento de infraestrutura cloud', 'SC');

INSERT INTO vaga_competencia (vaga_id, competencia_id) VALUES
-- Vaga 1 - Arroz (Java, Spring, SQL)
(1, 1), (1, 2), (1, 3),
-- Vaga 2 - Imperio (JS, React)
(2, 4), (2, 5),
-- Vaga 3 - Brocrosoft (Python, SQL, AWS)
(3, 7), (3, 3), (3, 8),
-- Vaga 4 - DevMaster (Groovy, Java)
(4, 9), (4, 1),
-- Vaga 5 - Caixa (AWS, Python)
(5, 8), (5, 7);

-- Match: Alice (ID 1) - Vaga Java (ID 1)
INSERT INTO curtida (candidato_id, vaga_id, origem_curtida) VALUES 
(1, 1, 'CANDIDATO'),
(1, 1, 'EMPRESA');

-- Nao Match: Joao (ID 2) - Vaga Frontend (ID 2)
INSERT INTO curtida (candidato_id, vaga_id, origem_curtida) VALUES 
(2, 2, 'CANDIDATO');