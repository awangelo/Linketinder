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

    void listarTodos(final HttpServerExchange exchange) {
        List<Vaga> lista = vagaService.listarTodos()

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

        Vaga vaga = vagaService.buscarPorId(id)
        if (!vaga) {
            exchange.statusCode = 404
            exchange.responseSender.send("nao encontrado")
            return
        }

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(vaga))
    }
}
