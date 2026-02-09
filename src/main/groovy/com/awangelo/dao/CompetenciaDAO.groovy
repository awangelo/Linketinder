package com.awangelo.dao

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import com.awangelo.db.ConnectionFactory
import com.awangelo.model.Competencia

class CompetenciaDAO {
    private Sql sql = ConnectionFactory.getSql()

    // Retorna ID da competencia existente ou cria e retorna o id novo
    Integer getIdOrCreate(Competencia competencia) {
        GroovyRowResult row = sql.firstRow('SELECT id FROM competencia WHERE nome = ?', [competencia.name()])
        if (row?.id) {
            row.id as Integer
        }

        // Inserir novo
        GroovyRowResult inserted = sql.firstRow('INSERT INTO competencia (nome) VALUES (?) RETURNING id', [competencia.name()])
        inserted?.id as Integer
    }

    List<Competencia> listarTodos() {
        List<GroovyRowResult> rows = sql.rows('SELECT nome FROM competencia')
        rows.collect { r -> Competencia.valueOf((r.nome) as String) }
    }

    Competencia buscarPorNome(String nome) {
        GroovyRowResult row = sql.firstRow('SELECT nome FROM competencia WHERE nome = ?', [nome])
        row ? Competencia.valueOf((row.nome) as String) : null
    }

    Integer update(String nomeAntigo, String nomeNovo) {
        GroovyRowResult r = sql.firstRow('UPDATE competencia SET nome = ? WHERE nome = ? RETURNING id', [nomeNovo, nomeAntigo])
        r?.id as Integer
    }

    boolean deleteByName(String nome) {
        Integer affected = sql.executeUpdate('DELETE FROM competencia WHERE nome = ?', [nome])
        affected > 0
    }
}
