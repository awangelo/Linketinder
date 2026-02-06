-- Candidatos e suas competencias
SELECT c.nome, c.sobrenome, comp.nome AS competencia
FROM candidato c
JOIN candidato_competencia cc ON c.id = cc.candidato_id
JOIN competencia comp ON comp.id = cc.competencia_id
ORDER BY c.nome;

-- Vagas da empresa Arroz Gostoso
SELECT e.nome AS empresa, v.nome AS vaga, v.local_vaga
FROM empresa e
JOIN vaga v ON e.id = v.empresa_id
WHERE e.nome = 'Arroz Gostoso';

-- Match
SELECT c.nome AS candidato, v.nome AS vaga
FROM curtida cur1
JOIN curtida cur2 ON cur1.candidato_id = cur2.candidato_id AND cur1.vaga_id = cur2.vaga_id
JOIN candidato c ON c.id = cur1.candidato_id
JOIN vaga v ON v.id = cur1.vaga_id
WHERE cur1.origem_curtida = 'CANDIDATO'
AND cur2.origem_curtida = 'EMPRESA';