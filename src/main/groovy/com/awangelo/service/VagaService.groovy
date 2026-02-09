package com.awangelo.service

import com.awangelo.model.Vaga
import com.awangelo.model.Empresa
import com.awangelo.dao.IVagaDAO
import com.awangelo.dao.VagaDAO

class VagaService {
    private IVagaDAO vagaDAO

    VagaService(IVagaDAO vagaDAO) {
        this.vagaDAO = vagaDAO
    }

    VagaService() {
        this.vagaDAO = new VagaDAO()
    }

    List<Vaga> listarTodos() {
        vagaDAO.listarTodos()
    }

    Vaga buscarPorId(Integer id) {
        vagaDAO.buscarPorId(id)
    }

    List<Vaga> buscarPorEmpresa(Empresa empresa) {
        vagaDAO.listarTodos().findAll { it.empresa?.id == empresa?.id }
    }

    void adicionar(Vaga vaga) {
        Integer id = vagaDAO.inserir(vaga)
        vaga.id = id
    }

    void atualizar(Vaga vaga) {
        vagaDAO.update(vaga)
    }

    boolean remover(Integer id) {
        vagaDAO.delete(id)
    }
}
