package com.awangelo.dao

import com.awangelo.model.Candidato
import com.awangelo.model.Vaga
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import com.awangelo.db.ConnectionFactory
import com.awangelo.model.Curtida

class CurtidaDAO {
    private Sql sql = ConnectionFactory.getSql()
    private CandidatoDAO candidatoDAO = new CandidatoDAO()
    private EmpresaDAO empresaDAO = new EmpresaDAO()
    private VagaDAO vagaDAO = new VagaDAO()

    Map inserirCurtida(Integer candidatoId, Integer vagaId, String origem) {
        return sql.withTransaction {
            GroovyRowResult r = sql.firstRow('INSERT INTO curtida (candidato_id, vaga_id, origem_curtida) VALUES (?, ?, ?) RETURNING id', [candidatoId, vagaId, origem])
            Integer id = r?.id as Integer

            // Verifica se existe a curtida oposta (vai ser match)
            String oposta = origem == 'CANDIDATO' ? 'EMPRESA' : 'CANDIDATO'
            GroovyRowResult other = sql.firstRow('SELECT id FROM curtida WHERE candidato_id = ? AND vaga_id = ? AND origem_curtida = ?', [candidatoId, vagaId, oposta])

            return [id: id, isMatch: other != null]
        }
    }

    List<Curtida> listarTodos() {
        List<GroovyRowResult> rows = sql.rows('SELECT id, candidato_id, vaga_id, origem_curtida FROM curtida')

        rows.collect { r ->
            Candidato candidato = candidatoDAO.buscarPorId(r.candidato_id as Integer)
            Vaga vaga = vagaDAO.buscarPorId(r.vaga_id as Integer, empresaDAO)

            new Curtida(
                id: r.id as Integer,
                candidato: candidato,
                vaga: vaga,
                origemCurtida: r.origem_curtida as String
            )
        }
    }

    List<Curtida> listarMatches() {
        List<GroovyRowResult> rows = sql.rows("""
            SELECT cur1.id, cur1.candidato_id, cur1.vaga_id
            FROM curtida cur1
            JOIN curtida cur2 ON cur1.candidato_id = cur2.candidato_id AND cur1.vaga_id = cur2.vaga_id
            WHERE cur1.origem_curtida = 'CANDIDATO' AND cur2.origem_curtida = 'EMPRESA'
        """)

        rows.collect { r ->
            Candidato candidato = candidatoDAO.buscarPorId(r.candidato_id as Integer)
            Vaga vaga = vagaDAO.buscarPorId(r.vaga_id as Integer, empresaDAO)

            new Curtida(
                id: r.id as Integer,
                candidato: candidato,
                vaga: vaga,
                origemCurtida: 'MATCH'
            )
        }
    }

    List<Curtida> listarCurtidasPorEmpresa(Integer empresaId) {
        List<GroovyRowResult> rows = sql.rows('SELECT id, candidato_id, vaga_id, origem_curtida FROM curtida WHERE vaga_id IN (SELECT id FROM vaga WHERE empresa_id = ?)', [empresaId])

        rows.collect { r ->
            Candidato candidato = candidatoDAO.buscarPorId(r.candidato_id as Integer)
            Vaga vaga = vagaDAO.buscarPorId(r.vaga_id as Integer, empresaDAO)

            new Curtida(
                id: r.id as Integer,
                candidato: candidato,
                vaga: vaga,
                origemCurtida: r.origem_curtida as String
            )
        }
    }

    List<Curtida> listarCurtidasPorCandidato(Integer candidatoId) {
        List<GroovyRowResult> rows = sql.rows('SELECT id, candidato_id, vaga_id, origem_curtida FROM curtida WHERE candidato_id = ?', [candidatoId])

        rows.collect { r ->
            Candidato candidato = candidatoDAO.buscarPorId(r.candidato_id as Integer)
            Vaga vaga = vagaDAO.buscarPorId(r.vaga_id as Integer, empresaDAO)

            new Curtida(
                id: r.id as Integer,
                candidato: candidato,
                vaga: vaga,
                origemCurtida: r.origem_curtida as String
            )
        }
    }

    boolean delete(Integer id) {
        Integer affected = sql.executeUpdate('DELETE FROM curtida WHERE id = ?', [id])
        return affected > 0
    }
}
