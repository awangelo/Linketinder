package com.awangelo.dao

import com.awangelo.model.Empresa

interface IEmpresaDAO {
    List<Empresa> listarTodos()
    Empresa buscarPorId(Integer id)
    Integer inserir(Empresa empresa)
    Integer update(Empresa empresa)
    boolean delete(Integer id)
}

