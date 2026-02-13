package com.awangelo.router

import com.awangelo.controller.*
import io.undertow.server.RoutingHandler

final class Router {

    static getRouter(CandidatoController candidatoController,
                     EmpresaController empresaController,
                     VagaController vagaController) {

        new RoutingHandler()
                .get("/candidatos", candidatoController.&listarTodos)
                .post("/candidatos", candidatoController.&criar)
                .get("/candidatos/{id}", candidatoController.&buscarPorId)
                .get("/empresas", empresaController.&listarTodos)
                .post("/empresas", empresaController.&criar)
                .get("/empresas/{id}", empresaController.&buscarPorId)
                .get("/vagas", vagaController.&listarTodos)
                .post("/vagas", vagaController.&criar)
                .get("/vagas/{id}", vagaController.&buscarPorId)
    }
}
