package com.awangelo.dao

import com.awangelo.model.Vaga

interface IVagaDAO {
    List<Vaga> listarTodos()
    Vaga buscarPorId(Integer id)
    Integer inserir(Vaga vaga)
    Integer update(Vaga vaga)
    boolean delete(Integer id)
}

