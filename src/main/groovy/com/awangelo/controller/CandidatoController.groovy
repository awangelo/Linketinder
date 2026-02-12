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

    void listarTodos(final HttpServerExchange exchange) {
        List<Candidato> lista = candidatoService.listarTodos()

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(lista))
    }

    void buscarPorId(final HttpServerExchange exchange) {
        String idStr = exchange.getQueryParameters().get("id")?.peekFirst()
        if (!idStr || idStr.isEmpty()) {
            exchange.statusCode = 400
            exchange.responseSender.send("id obrigatorio")
            return
        }

        Integer id
        try {
            id = idStr as Integer
        } catch (NumberFormatException ignored) {
            exchange.statusCode = 400
            exchange.responseSender.send("id invalido")
            return
        }

        if (id < 0) {
            exchange.statusCode = 400
            exchange.responseSender.send("id invalido")
            return
        }

        Candidato candidato = candidatoService.buscarPorId(id)
        if (!candidato) {
            exchange.statusCode = 404
            exchange.responseSender.send("nao encontrado")
            return
        }

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(candidato))
    }
}
