package com.awangelo.service

import com.awangelo.model.Curtida
import com.awangelo.model.Candidato
import com.awangelo.model.Empresa
import com.awangelo.model.Vaga

class CurtidaService {
    private List<Curtida> curtidas = []
    private Integer proximoId = 1

    Curtida candidatoCurteVaga(Candidato candidato, Vaga vaga) {
        Curtida curtidaExistente = curtidas.find {
            it.candidato?.id == candidato.id && it.vaga?.id == vaga.id
        }

        if (curtidaExistente) {
            return curtidaExistente
        }

        Curtida curtida = new Curtida(
            id: proximoId++,
            candidato: candidato,
            vaga: vaga
        )
        curtidas.add(curtida)
        return curtida
    }

    Curtida empresaCurteCandidato(Empresa empresa, Candidato candidato, Vaga vaga) {
        Curtida curtidaExistente = curtidas.find {
            it.candidato?.id == candidato.id && it.vaga?.id == vaga.id
        }

        if (curtidaExistente) {
            curtidaExistente.empresa = empresa
            return curtidaExistente
        }

        Curtida curtida = new Curtida(
            id: proximoId++,
            candidato: candidato,
            vaga: vaga,
            empresa: empresa
        )
        curtidas.add(curtida)
        return curtida
    }

    List<Curtida> listarTodos() {
        return curtidas
    }

    List<Curtida> listarMatches() {
        return curtidas.findAll { it.isMatch() }
    }

    List<Curtida> listarCurtidasPorEmpresa(Empresa empresa) {
        return curtidas.findAll { it.vaga?.empresa?.id == empresa.id }
    }

    List<Curtida> listarCurtidasPorCandidato(Candidato candidato) {
        return curtidas.findAll { it.candidato?.id == candidato.id }
    }
}
