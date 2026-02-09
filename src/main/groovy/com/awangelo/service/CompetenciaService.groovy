package com.awangelo.service

import com.awangelo.model.Competencia
import com.awangelo.dao.ICompetenciaDAO
import com.awangelo.dao.CompetenciaDAO

class CompetenciaService {
    private ICompetenciaDAO competenciaDAO

    CompetenciaService(ICompetenciaDAO competenciaDAO) {
        this.competenciaDAO = competenciaDAO
    }

    CompetenciaService() {
        this.competenciaDAO = new CompetenciaDAO()
    }

    List<Competencia> listarTodos() {
        competenciaDAO.listarTodos()
    }

    Integer adicionar(Competencia comp) {
        competenciaDAO.getIdOrCreate(comp)
    }

    Integer renomear(String antigoNome, String novoNome) {
        competenciaDAO.update(antigoNome, novoNome)
    }

    boolean remover(String nome) {
        competenciaDAO.deleteByName(nome)
    }
}
