package com.awangelo.controller

import com.awangelo.model.Vaga
import com.awangelo.service.VagaService
import groovy.json.JsonOutput
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers

final class VagaController {
    private final VagaService vagaService

    VagaController(VagaService vagaService) {
        this.vagaService = vagaService
    }

    void listarTodos(final HttpServerExchange exchange) throws Exception {
        List<Vaga> lista = vagaService.listarTodos()

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(lista))
    }
}
