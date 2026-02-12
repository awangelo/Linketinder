package com.awangelo.controller

import com.awangelo.model.Empresa
import com.awangelo.service.EmpresaService
import groovy.json.JsonOutput
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers

final class EmpresaController {
    private final EmpresaService empresaService

    EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService
    }

    void listarTodos(final HttpServerExchange exchange) throws Exception {
        List<Empresa> lista = empresaService.listarTodos()

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(lista))
    }
}
