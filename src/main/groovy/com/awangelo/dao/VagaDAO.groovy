package com.awangelo.dao

import com.awangelo.model.Empresa
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import com.awangelo.db.ConnectionFactory
import com.awangelo.model.Vaga
import com.awangelo.model.Competencia

class VagaDAO implements IVagaDAO {
    private Sql sql
    private ICompetenciaDAO competenciaDAO
    private IEmpresaDAO empresaDAO

    VagaDAO(Sql sql, ICompetenciaDAO competenciaDAO, IEmpresaDAO empresaDAO) {
        this.sql = sql
        this.competenciaDAO = competenciaDAO
        this.empresaDAO = empresaDAO
    }

    VagaDAO() {
        this.sql = ConnectionFactory.getSql()
        this.competenciaDAO = new CompetenciaDAO(this.sql)
        this.empresaDAO = new EmpresaDAO(this.sql)
    }

    List<Vaga> listarTodos() {
        List<GroovyRowResult> rows = sql.rows('SELECT id, nome, descricao, local_vaga, empresa_id FROM vaga')

        rows.collect { r ->
            Integer vagaId = r['id'] as Integer
            Integer empresaId = r['empresa_id'] as Integer
            Empresa empresa = empresaDAO.buscarPorId(empresaId)
            List<Competencia> competencias = sql.rows('SELECT c.nome FROM competencia c JOIN vaga_competencia vc ON c.id = vc.competencia_id WHERE vc.vaga_id = ?', [vagaId]).collect { rc -> Competencia.valueOf((rc.nome) as String) }

            new Vaga(
                    id: vagaId,
                    nome: r.nome as String,
                    descricao: r.descricao as String,
                    localVaga: r.local_vaga as String,
                    empresa: empresa,
                    competencias: competencias
            )
        }
    }

    Vaga buscarPorId(Integer id) {
        GroovyRowResult r = sql.firstRow('SELECT id, nome, descricao, local_vaga, empresa_id FROM vaga WHERE id = ?', [id])
        if (!r) return null

        Integer vagaId = r['id'] as Integer
        Integer empresaId = r['empresa_id'] as Integer
        Empresa empresa = empresaDAO.buscarPorId(empresaId)
        List<Competencia> competencias = sql.rows('SELECT c.nome FROM competencia c JOIN vaga_competencia vc ON c.id = vc.competencia_id WHERE vc.vaga_id = ?', [vagaId]).collect { rc -> Competencia.valueOf((rc.nome) as String) }

        new Vaga(
                id: vagaId,
                nome: r.nome as String,
                descricao: r.descricao as String,
                localVaga: r.local_vaga as String,
                empresa: empresa,
                competencias: competencias
        )
    }

    Integer inserir(Vaga vaga) {
        return sql.withTransaction {
            GroovyRowResult r = sql.firstRow('INSERT INTO vaga (empresa_id, nome, descricao, local_vaga) VALUES (?, ?, ?, ?) RETURNING id', [vaga.empresa.id, vaga.nome, vaga.descricao, vaga.localVaga])
            Integer vagaId = r?.id as Integer
            if (vaga.competencias) {
                vaga.competencias.each { comp ->
                    Integer compId = competenciaDAO.getIdOrCreate(comp)
                    sql.executeInsert('INSERT INTO vaga_competencia (vaga_id, competencia_id) VALUES (?, ?) ON CONFLICT DO NOTHING', [vagaId, compId])
                }
            }
            vagaId
        }
    }

    Integer update(Vaga vaga) {
        return sql.withTransaction {
            sql.executeUpdate('UPDATE vaga SET nome = ?, descricao = ?, local_vaga = ?, empresa_id = ? WHERE id = ?', [vaga.nome, vaga.descricao, vaga.localVaga, vaga.empresa.id, vaga.id])
            // refresh competencias
            sql.executeUpdate('DELETE FROM vaga_competencia WHERE vaga_id = ?', [vaga.id])
            if (vaga.competencias) {
                vaga.competencias.each { comp ->
                    Integer compId = competenciaDAO.getIdOrCreate(comp)
                    sql.executeInsert('INSERT INTO vaga_competencia (vaga_id, competencia_id) VALUES (?, ?) ON CONFLICT DO NOTHING', [vaga.id, compId])
                }
            }
            vaga.id
        }
    }

    boolean delete(Integer id) {
        Integer affected = sql.executeUpdate('DELETE FROM vaga WHERE id = ?', [id])
        affected > 0
    }
}
