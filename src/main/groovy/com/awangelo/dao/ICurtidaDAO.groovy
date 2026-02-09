package com.awangelo.dao

import com.awangelo.model.Curtida

interface ICurtidaDAO {
    Map inserirCurtida(Integer candidatoId, Integer vagaId, String origem)
    List<Curtida> listarTodos()
    List<Curtida> listarMatches()
    List<Curtida> listarCurtidasPorEmpresa(Integer empresaId)
    List<Curtida> listarCurtidasPorCandidato(Integer candidatoId)
    boolean delete(Integer id)
}

