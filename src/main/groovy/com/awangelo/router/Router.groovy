package com.awangelo.router

import com.awangelo.controller.*
import io.undertow.server.RoutingHandler

final class Router {

    static getRouter(CandidatoController candidatoController,
                     EmpresaController empresaController,
                     VagaController vagaController) {
        new RoutingHandler()
                .get("/candidatos", candidatoController.&listarTodos)
                .get("/candidatos/{id}", candidatoController.&buscarPorId)
                .get("/empresas", empresaController.&listarTodos)
                .get("/empresas/{id}", empresaController.&buscarPorId)
                .get("/vagas", vagaController.&listarTodos)
                .get("/vagas/{id}", vagaController.&buscarPorId)
    }
}
