package com.awangelo.dao

import com.awangelo.model.Competencia

interface ICompetenciaDAO {
    Integer getIdOrCreate(Competencia competencia)
    List<Competencia> listarTodos()
    Competencia buscarPorNome(String nome)
    Integer update(String nomeAntigo, String nomeNovo)
    boolean deleteByName(String nome)
}

