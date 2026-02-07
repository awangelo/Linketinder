package com.awangelo.service

import com.awangelo.model.Vaga
import com.awangelo.model.Empresa
import com.awangelo.dao.VagaDAO
import com.awangelo.dao.EmpresaDAO

class VagaService {
    private VagaDAO vagaDAO = new VagaDAO()
    private EmpresaDAO empresaDAO = new EmpresaDAO()

    VagaService() {
        // compatibilidade
    }

    List<Vaga> listarTodos() {
        return vagaDAO.listarTodos(empresaDAO)
    }

    Vaga buscarPorId(Integer id) {
        return vagaDAO.buscarPorId(id, empresaDAO)
    }

    List<Vaga> buscarPorEmpresa(Empresa empresa) {
        return vagaDAO.listarTodos(empresaDAO).findAll { it.empresa?.id == empresa?.id }
    }

    void adicionar(Vaga vaga) {
        Integer id = vagaDAO.inserir(vaga)
        vaga.id = id
    }

    void atualizar(Vaga vaga) {
        vagaDAO.update(vaga)
    }

    boolean remover(Integer id) {
        return vagaDAO.delete(id)
    }
}
