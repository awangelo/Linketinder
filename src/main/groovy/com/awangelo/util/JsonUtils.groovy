package com.awangelo.util

import com.awangelo.model.Competencia
import groovy.json.JsonOutput
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers

final class JsonUtils {

    static List<Competencia> convertCompetencias(List<String> competenciasStr) {
        if (!competenciasStr) {
            return []
        }

        try {
            return competenciasStr.collect { Competencia.valueOf(it.toUpperCase()) }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Competencia invalida: ${e.message}")
        }
    }

    static void sendJsonResponse(HttpServerExchange exchange, int statusCode, Object data) {
        exchange.statusCode = statusCode
        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(data))
    }

    static void sendError(HttpServerExchange exchange, int statusCode, String message) {
        exchange.statusCode = statusCode
        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send("{\"error\":\"${message}\"}")
    }
}

