package com.awangelo.dao

import com.awangelo.model.Candidato

interface ICandidatoDAO {
    List<Candidato> listarTodos()
    Candidato buscarPorId(Integer id)
    Integer inserir(Candidato candidato)
    Integer update(Candidato candidato)
    boolean delete(Integer id)
}

