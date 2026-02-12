package com.awangelo.controller

import com.awangelo.model.Candidato
import com.awangelo.service.CandidatoService
import groovy.json.JsonOutput
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers

final class CandidatoController {
    private final CandidatoService candidatoService

    CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService
    }

    void listarTodos(final HttpServerExchange exchange) throws Exception {
        List<Candidato> lista = candidatoService.listarTodos()

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(lista))
    }
}
