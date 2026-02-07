package com.awangelo.service

import com.awangelo.model.Curtida
import com.awangelo.model.Candidato
import com.awangelo.model.Empresa
import com.awangelo.model.Vaga
import com.awangelo.dao.CurtidaDAO

class CurtidaService {
    private CurtidaDAO curtidaDAO = new CurtidaDAO()

    Curtida candidatoCurteVaga(Candidato candidato, Vaga vaga) {
        if (!candidato?.id || !vaga?.id) {
            throw new IllegalArgumentException("Candidato ou Vaga invalidos")
        }
        def result = curtidaDAO.inserirCurtida(candidato.id, vaga.id, 'CANDIDATO')
        def curtida = new Curtida(
            id: result.id as Integer,
            candidato: candidato,
            vaga: vaga,
            origemCurtida: 'CANDIDATO'
        )

        if (result.isMatch) {
            curtida.origemCurtida = 'MATCH'
        }
        return curtida
    }

    Curtida empresaCurteCandidato(Empresa empresa, Candidato candidato, Vaga vaga) {
        if (!candidato?.id || !vaga?.id) {
            throw new IllegalArgumentException("Candidato ou Vaga invalidos")
        }
        def result = curtidaDAO.inserirCurtida(candidato.id, vaga.id, 'EMPRESA')
        def curtida = new Curtida(
            id: result.id as Integer,
            candidato: candidato,
            vaga: vaga,
            origemCurtida: 'EMPRESA'
        )
        // Se houve match, marcar como tal
        if (result.isMatch) {
            curtida.origemCurtida = 'MATCH'
        }
        return curtida
    }

    List<Curtida> listarTodos() {
        return curtidaDAO.listarTodos()
    }

    List<Curtida> listarMatches() {
        return curtidaDAO.listarMatches()
    }

    List<Curtida> listarCurtidasPorEmpresa(Empresa empresa) {
        return curtidaDAO.listarCurtidasPorEmpresa(empresa.id)
    }

    List<Curtida> listarCurtidasPorCandidato(Candidato candidato) {
        return curtidaDAO.listarCurtidasPorCandidato(candidato.id)
    }
}
