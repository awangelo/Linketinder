package com.awangelo.service

import com.awangelo.model.Candidato
import com.awangelo.dao.CandidatoDAO

class CandidatoService {
    private CandidatoDAO candidatoDAO = new CandidatoDAO()

    List<Candidato> listarTodos() {
        candidatoDAO.listarTodos()
    }

    Candidato buscarPorId(Integer id) {
        candidatoDAO.buscarPorId(id)
    }

    void adicionar(Candidato candidato) {
        Integer id = candidatoDAO.inserir(candidato)
        candidato.id = id
    }

    void atualizar(Candidato candidato) {
        candidatoDAO.update(candidato)
    }

    boolean remover(Integer id) {
        candidatoDAO.delete(id)
    }
}
