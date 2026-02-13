package com.awangelo.controller

import com.awangelo.model.Competencia
import com.awangelo.model.Empresa
import com.awangelo.model.Vaga
import com.awangelo.service.EmpresaService
import com.awangelo.service.VagaService
import com.awangelo.util.JsonUtils
import com.awangelo.util.ValidationUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers
import java.nio.charset.StandardCharsets

final class VagaController {

    private final VagaService vagaService
    private final EmpresaService empresaService

    VagaController(VagaService vagaService, EmpresaService empresaService) {
        this.vagaService = vagaService
        this.empresaService = empresaService
    }

    void listarTodos(final HttpServerExchange exchange) {
        List<Vaga> lista = vagaService.listarTodos()

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(lista))
    }

    void criar(final HttpServerExchange exchange) {
        if (exchange.isInIoThread()) {
            exchange.dispatch { criar(exchange) }
            return
        }

        exchange.requestReceiver.receiveFullString(
                { HttpServerExchange exch, String body ->
                    try {
                        def jsonSlurper = new JsonSlurper()
                        Map data = jsonSlurper.parseText(body) as Map

                        String error = ValidationUtils.validateRequired(data, 'nome')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateRequired(data, 'descricao')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateRequired(data, 'localVaga')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateRequired(data, 'empresaId')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        Integer empresaId
                        try {
                            empresaId = data.empresaId as Integer
                        } catch (Exception ignored) {
                            JsonUtils.sendError(exchange, 400, 'empresaId invalido')
                            return
                        }

                        error = ValidationUtils.validatePositiveId(empresaId)
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        Empresa empresa = empresaService.buscarPorId(empresaId)
                        if (!empresa) {
                            JsonUtils.sendError(exchange, 404, 'empresa nao encontrada')
                            return
                        }

                        error = ValidationUtils.validateCompetencias(data.competencias as List)
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        List<Competencia> competencias
                        try {
                            competencias = JsonUtils.convertCompetencias(data.competencias as List<String>)
                        } catch (IllegalArgumentException e) {
                            JsonUtils.sendError(exchange, 400, e.message)
                            return
                        }

                        Vaga vaga = new Vaga(
                                nome: data.nome as String,
                                descricao: data.descricao as String,
                                localVaga: data.localVaga as String,
                                empresa: empresa,
                                competencias: competencias
                        )

                        vagaService.adicionar(vaga)

                        Map resposta = [
                                id          : vaga.id,
                                nome        : vaga.nome,
                                descricao   : vaga.descricao,
                                localVaga   : vaga.localVaga,
                                empresaId   : empresa.id,
                                competencias: vaga.competencias.collect { it.toString() }
                        ]

                        JsonUtils.sendJsonResponse(exchange, 201, resposta)

                    } catch (Exception e) {
                        JsonUtils.sendError(exchange, 400, "erro ao processar requisicao: ${e.message}")
                    }
                }, StandardCharsets.UTF_8)
    }

    void buscarPorId(final HttpServerExchange exchange) {
        Integer id = ValidationUtils.extractIdFromPath(exchange)
        if (!id) {
            return
        }

        Vaga vaga = vagaService.buscarPorId(id)
        if (!vaga) {
            JsonUtils.sendError(exchange, 404, "nao encontrado")
            return
        }

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(vaga))
    }
}
