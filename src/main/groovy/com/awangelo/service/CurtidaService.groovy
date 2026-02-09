package com.awangelo.service

import com.awangelo.model.Curtida
import com.awangelo.model.Candidato
import com.awangelo.model.Empresa
import com.awangelo.model.Vaga
import com.awangelo.dao.ICurtidaDAO
import com.awangelo.dao.CurtidaDAO

class CurtidaService {
    private ICurtidaDAO curtidaDAO

    CurtidaService(ICurtidaDAO curtidaDAO) {
        this.curtidaDAO = curtidaDAO
    }

    CurtidaService() {
        this.curtidaDAO = new CurtidaDAO()
    }

    Curtida candidatoCurteVaga(Candidato candidato, Vaga vaga) {
        if (!candidato?.id || !vaga?.id) {
            throw new IllegalArgumentException("Candidato ou Vaga invalidos")
        }
        Map result = curtidaDAO.inserirCurtida(candidato.id, vaga.id, 'CANDIDATO')
        Curtida curtida = new Curtida(
            id: result.id as Integer,
            candidato: candidato,
            vaga: vaga,
            origemCurtida: 'CANDIDATO'
        )

        if (result.isMatch) {
            curtida.origemCurtida = 'MATCH'
        }
        curtida
    }

    Curtida empresaCurteCandidato(Empresa empresa, Candidato candidato, Vaga vaga) {
        if (!candidato?.id || !vaga?.id) {
            throw new IllegalArgumentException("Candidato ou Vaga invalidos")
        }
        Map result = curtidaDAO.inserirCurtida(candidato.id, vaga.id, 'EMPRESA')
        Curtida curtida = new Curtida(
            id: result.id as Integer,
            candidato: candidato,
            vaga: vaga,
            origemCurtida: 'EMPRESA'
        )
        // Se houve match, marcar como tal
        if (result.isMatch) {
            curtida.origemCurtida = 'MATCH'
        }
        curtida
    }

    List<Curtida> listarTodos() {
        curtidaDAO.listarTodos()
    }

    List<Curtida> listarMatches() {
        curtidaDAO.listarMatches()
    }

    List<Curtida> listarCurtidasPorEmpresa(Empresa empresa) {
        curtidaDAO.listarCurtidasPorEmpresa(empresa.id)
    }

    List<Curtida> listarCurtidasPorCandidato(Candidato candidato) {
        curtidaDAO.listarCurtidasPorCandidato(candidato.id)
    }
}
