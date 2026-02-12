package com.awangelo.util

import io.undertow.server.HttpServerExchange

import java.time.LocalDate
import java.time.Period

final class ValidationUtils {

    static Integer extractIdFromPath(HttpServerExchange exchange) {
        String idStr = exchange.getQueryParameters().get("id")?.peekFirst()

        if (!idStr || idStr.isEmpty()) {
            JsonUtils.sendError(exchange, 400, "id obrigatorio")
            return null
        }

        Integer id
        try {
            id = idStr as Integer
        } catch (NumberFormatException ignored) {
            JsonUtils.sendError(exchange, 400, "id invalido")
            return null
        }

        if (id < 0) {
            JsonUtils.sendError(exchange, 400, "id invalido")
            return null
        }

        id
    }

    static String validateRequired(Map data, String field) {
        if (!data.containsKey(field) || !data[field] || data[field].toString().isEmpty()) {
            return "campo ${field} obrigatorio"
        }

        null
    }

    static String validateEmail(String email) {
        if (!email?.contains("@") || !email?.contains(".")) {
            return "email invalido"
        }

        null
    }

    static String validateCpf(String cpf) {
        String cleanCpf = cpf?.replaceAll("[^0-9]", "")
        if (cleanCpf?.length() != 11) {
            return "cpf invalido"
        }

        null
    }

    static String validateCnpj(String cnpj) {
        String cleanCnpj = cnpj?.replaceAll("[^0-9]", "")
        if (cleanCnpj?.length() != 14) {
            return "cnpj invalido"
        }

        null
    }

    static String validateCep(String cep) {
        String cleanCep = cep?.replaceAll("[^0-9]", "")
        if (cleanCep?.length() != 8) {
            return "cep invalido"
        }

        null
    }

    static String validateSenha(String senha) {
        if (!senha || senha.length() < 6) {
            return "senha deve ter no minimo 6 caracteres"
        }

        null
    }

    static String validateIdadeMinima(LocalDate dataNascimento, int idadeMinima = 18) {
        if (!dataNascimento) {
            return "data de nascimento invalida"
        }

        int idade = Period.between(dataNascimento, LocalDate.now()).years
        if (idade < idadeMinima) {
            return "idade minima de ${idadeMinima} anos nao atingida"
        }

        null
    }

    static String validateCompetencias(List competencias) {
        if (!competencias || competencias.isEmpty()) {
            return "competencias nao podem estar vazias"
        }

        null
    }

    static String validatePositiveId(Integer id) {
        if (!id || id <= 0) {
            return "id invalido"
        }

        null
    }
}

