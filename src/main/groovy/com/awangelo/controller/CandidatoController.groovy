package com.awangelo.controller

import com.awangelo.model.Candidato
import com.awangelo.model.Competencia
import com.awangelo.service.CandidatoService
import com.awangelo.util.JsonUtils
import com.awangelo.util.ValidationUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.undertow.server.HttpServerExchange
import io.undertow.util.Headers
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeParseException

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

                        error = ValidationUtils.validateRequired(data, 'cpf')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateCpf(data.cpf as String)
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        error = ValidationUtils.validateRequired(data, 'dataNascimento')
                        if (error) {
                            JsonUtils.sendError(exchange, 400, error)
                            return
                        }

                        LocalDate dataNascimento
                        try {
                            dataNascimento = LocalDate.parse(data.dataNascimento as String)
                        } catch (DateTimeParseException ignored) {
                            JsonUtils.sendError(exchange, 400, 'data de nascimento invalida')
                            return
                        }

                        error = ValidationUtils.validateIdadeMinima(dataNascimento, 18)
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

                        Candidato candidato = new Candidato(
                                nome: data.nome as String,
                                sobrenome: data.sobrenome as String ?: '',
                                email: data.email as String,
                                cpf: data.cpf as String,
                                dataNascimento: dataNascimento,
                                pais: data.pais as String ?: '',
                                estado: data.estado as String ?: '',
                                cep: data.cep as String,
                                descricao: data.descricao as String ?: '',
                                senha: data.senha as String,
                                telefone: data.telefone as String ?: '',
                                linkedin: data.linkedin as String ?: '',
                                competencias: competencias
                        )

                        candidatoService.adicionar(candidato)

                        // Resposta sem senha
                        Map resposta = [
                                id            : candidato.id,
                                nome          : candidato.nome,
                                sobrenome     : candidato.sobrenome,
                                email         : candidato.email,
                                cpf           : candidato.cpf,
                                dataNascimento: candidato.dataNascimento.toString(),
                                idade         : candidato.idade,
                                pais          : candidato.pais,
                                estado        : candidato.estado,
                                cep           : candidato.cep,
                                descricao     : candidato.descricao,
                                telefone      : candidato.telefone,
                                linkedin      : candidato.linkedin,
                                competencias  : candidato.competencias.collect { it.toString() }
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

        Candidato candidato = candidatoService.buscarPorId(id)
        if (!candidato) {
            JsonUtils.sendError(exchange, 404, "nao encontrado")
            return
        }

        exchange.responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
        exchange.responseSender.send(JsonOutput.toJson(candidato))
    }
}
