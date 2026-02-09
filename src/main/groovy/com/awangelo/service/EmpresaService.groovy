package com.awangelo.service

import com.awangelo.model.Empresa
import com.awangelo.dao.EmpresaDAO

class EmpresaService {
    private EmpresaDAO empresaDAO = new EmpresaDAO()

    EmpresaService() {
        // DAO-backed
    }

    List<Empresa> listarTodos() {
        empresaDAO.listarTodos()
    }

    Empresa buscarPorId(Integer id) {
        empresaDAO.buscarPorId(id)
    }

    void adicionar(Empresa empresa) {
        Integer id = empresaDAO.inserir(empresa)
        empresa.id = id
    }

    void atualizar(Empresa empresa) {
        empresaDAO.update(empresa)
    }

    boolean remover(Integer id) {
        empresaDAO.delete(id)
    }
}
