package com.awangelo.service

import com.awangelo.model.Competencia
import com.awangelo.dao.CompetenciaDAO

class CompetenciaService {
    private CompetenciaDAO competenciaDAO = new CompetenciaDAO()

    List<Competencia> listarTodos() {
        return competenciaDAO.listarTodos()
    }

    Integer adicionar(Competencia comp) {
        return competenciaDAO.getIdOrCreate(comp)
    }

    Integer renomear(String antigoNome, String novoNome) {
        return competenciaDAO.update(antigoNome, novoNome)
    }

    boolean remover(String nome) {
        return competenciaDAO.deleteByName(nome)
    }
}
