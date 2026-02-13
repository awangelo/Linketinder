package com.awangelo.controller

import com.awangelo.model.Empresa
import com.awangelo.service.EmpresaService
import com.awangelo.util.JsonUtils
import com.awangelo.util.ValidationUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers
import java.nio.charset.StandardCharsets

final class EmpresaController {

    private final EmpresaService empresaService

    EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService
    }

    void listarTodos(final HttpServerExchange exchange) {
        List<Empresa> lista = empresaService.listarTodos()

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

                        error = ValidationUtils.validateRequired(data, 'email')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateEmail(data.email as String)
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateRequired(data, 'cnpj')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateCnpj(data.cnpj as String)
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateRequired(data, 'cep')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateCep(data.cep as String)
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateRequired(data, 'senha')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateSenha(data.senha as String)
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        Empresa empresa = new Empresa(
                                nome: data.nome as String,
                                email: data.email as String,
                                cnpj: data.cnpj as String,
                                pais: data.pais as String ?: '',
                                estado: data.estado as String ?: '',
                                cep: data.cep as String,
                                descricao: data.descricao as String ?: '',
                                senha: data.senha as String
                        )

                        empresaService.adicionar(empresa)

                        // Resposta sem senha
                        Map resposta = [
                                id       : empresa.id,
                                nome     : empresa.nome,
                                email    : empresa.email,
                                cnpj     : empresa.cnpj,
                                pais     : empresa.pais,
                                estado   : empresa.estado,
                                cep      : empresa.cep,
                                descricao: empresa.descricao
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

        Empresa empresa = empresaService.buscarPorId(id)
        if (!empresa) {
            JsonUtils.sendError(exchange, 404, "nao encontrado")
            return
        }

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(empresa))
    }
}
