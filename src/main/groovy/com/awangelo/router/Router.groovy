package com.awangelo.router

import com.awangelo.controller.*
import io.undertow.server.RoutingHandler

final class Router {

    static getRouter(CandidatoController candidatoController,
                     EmpresaController empresaController,
                     VagaController vagaController) {
        new RoutingHandler()
                .get("/candidatos", candidatoController.&listarTodos)
                .get("/empresas", empresaController.&listarTodos)
                .get("/vagas", vagaController.&listarTodos)
    }
}
