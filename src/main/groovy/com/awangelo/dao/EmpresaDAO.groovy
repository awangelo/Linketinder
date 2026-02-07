package com.awangelo.dao

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import com.awangelo.db.ConnectionFactory
import com.awangelo.model.Empresa

class EmpresaDAO {
    private Sql sql = ConnectionFactory.getSql()

    List<Empresa> listarTodos() {
        List<GroovyRowResult> rows = sql.rows('SELECT id, nome, email, cnpj, pais, estado, cep, descricao FROM empresa')
        rows.collect { r ->
            Integer empId = r['id'] as Integer
            new Empresa(
                id: empId,
                nome: r.nome as String,
                email: r.email as String,
                cnpj: r.cnpj as String,
                pais: r.pais as String,
                estado: r.estado as String,
                cep: r.cep as String,
                descricao: r.descricao as String
            )
        }
    }

    Empresa buscarPorId(Integer id) {
        GroovyRowResult r = sql.firstRow('SELECT id, nome, email, cnpj, pais, estado, cep, descricao FROM empresa WHERE id = ?', [id])
        if (!r) return null

        Integer empId = r['id'] as Integer
        return new Empresa(
            id: empId,
            nome: r.nome as String,
            email: r.email as String,
            cnpj: r.cnpj as String,
            pais: r.pais as String,
            estado: r.estado as String,
            cep: r.cep as String,
            descricao: r.descricao as String
        )
    }

    Integer inserir(Empresa empresa) {
        return sql.withTransaction {
            def r = sql.firstRow(
                'INSERT INTO empresa (nome, cnpj, email, pais, estado, cep, descricao, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id',
                [empresa.nome, empresa.cnpj, empresa.email, empresa.pais, empresa.estado, empresa.cep, empresa.descricao, empresa.senha]
            )
            return r?.id as Integer
        }
    }

    Integer update(Empresa empresa) {
        return sql.withTransaction {
            sql.executeUpdate('UPDATE empresa SET nome = ?, email = ?, cnpj = ?, pais = ?, estado = ?, cep = ?, descricao = ?, senha = ? WHERE id = ?', [empresa.nome, empresa.email, empresa.cnpj, empresa.pais, empresa.estado, empresa.cep, empresa.descricao, empresa.senha, empresa.id])
            return empresa.id
        }
    }

    boolean delete(Integer id) {
        Integer affected = sql.executeUpdate('DELETE FROM empresa WHERE id = ?', [id])
        return affected > 0
    }
}
