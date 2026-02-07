package com.awangelo.dao

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import com.awangelo.db.ConnectionFactory
import com.awangelo.model.Candidato
import com.awangelo.model.Competencia

import java.time.LocalDate

class CandidatoDAO {
    private Sql sql = ConnectionFactory.getSql()
    private CompetenciaDAO competenciaDAO = new CompetenciaDAO()

    List<Candidato> listarTodos() {
        List<GroovyRowResult> rows = sql.rows('SELECT id, nome, sobrenome, email, cpf, data_nascimento, pais, estado, cep, descricao FROM candidato')

        rows.collect { r ->
            Integer candId = r['id'] as Integer
            def comps = sql.rows('SELECT c.nome FROM competencia c JOIN candidato_competencia cc ON c.id = cc.competencia_id WHERE cc.candidato_id = ?', [candId]).collect { cr -> Competencia.valueOf((cr.nome) as String) }
            def dataNasc = r.data_nascimento ? (r.data_nascimento as java.sql.Date).toLocalDate() : null
            new Candidato(
                id: candId,
                nome: r.nome as String,
                sobrenome: r.sobrenome as String,
                email: r.email as String,
                cpf: r.cpf as String,
                dataNascimento: dataNasc,
                pais: r.pais as String,
                estado: r.estado as String,
                cep: r.cep as String,
                descricao: r.descricao as String,
                competencias: comps
            )
        }
    }

    Candidato buscarPorId(Integer id) {
        GroovyRowResult r = sql.firstRow('SELECT id, nome, sobrenome, email, cpf, data_nascimento, pais, estado, cep, descricao FROM candidato WHERE id = ?', [id])
        if (!r) return null

        Integer candId = r['id'] as Integer
        List<Competencia> comps = sql.rows('SELECT c.nome FROM competencia c JOIN candidato_competencia cc ON c.id = cc.competencia_id WHERE cc.candidato_id = ?', [candId]).collect { cr -> Competencia.valueOf((cr.nome) as String) }
        LocalDate dataNasc = r.data_nascimento ? (r.data_nascimento as java.sql.Date).toLocalDate() : null
        return new Candidato(
            id: candId,
            nome: r.nome as String,
            sobrenome: r.sobrenome as String,
            email: r.email as String,
            cpf: r.cpf as String,
            dataNascimento: dataNasc,
            pais: r.pais as String,
            estado: r.estado as String,
            cep: r.cep as String,
            descricao: r.descricao as String,
            competencias: comps
        )
    }

    Integer inserir(Candidato candidato) {
        return sql.withTransaction {
            def r = sql.firstRow('INSERT INTO candidato (nome, sobrenome, data_nascimento, email, cpf, pais, estado, cep, descricao, senha, telefone, linkedin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id', [candidato.nome, candidato.sobrenome, candidato.dataNascimento, candidato.email, candidato.cpf, candidato.pais, candidato.estado, candidato.cep, candidato.descricao, candidato.senha, candidato.telefone, candidato.linkedin])
            Integer candId = r?.id as Integer
            if (candidato.competencias) {
                candidato.competencias.each { comp ->
                    Integer compId = competenciaDAO.getIdOrCreate(comp)
                    sql.executeInsert('INSERT INTO candidato_competencia (candidato_id, competencia_id) VALUES (?, ?) ON CONFLICT DO NOTHING', [candId, compId])
                }
            }
            return candId
        }
    }

    Integer update(Candidato candidato) {
        return sql.withTransaction {
            sql.executeUpdate('UPDATE candidato SET nome = ?, sobrenome = ?, data_nascimento = ?, email = ?, cpf = ?, pais = ?, estado = ?, cep = ?, descricao = ?, senha = ?, telefone = ?, linkedin = ? WHERE id = ?', [candidato.nome, candidato.sobrenome, candidato.dataNascimento, candidato.email, candidato.cpf, candidato.pais, candidato.estado, candidato.cep, candidato.descricao, candidato.senha, candidato.telefone, candidato.linkedin, candidato.id])
            // refresh competencias: delete existing then insert new
            sql.executeUpdate('DELETE FROM candidato_competencia WHERE candidato_id = ?', [candidato.id])
            if (candidato.competencias) {
                candidato.competencias.each { comp ->
                    Integer compId = competenciaDAO.getIdOrCreate(comp)
                    sql.executeInsert('INSERT INTO candidato_competencia (candidato_id, competencia_id) VALUES (?, ?) ON CONFLICT DO NOTHING', [candidato.id, compId])
                }
            }
            return candidato.id
        }
    }

    boolean delete(Integer id) {
        def affected = sql.executeUpdate('DELETE FROM candidato WHERE id = ?', [id])
        return affected > 0
    }
}
