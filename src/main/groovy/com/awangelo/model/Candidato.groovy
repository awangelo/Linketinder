package com.awangelo.model

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class Candidato implements Entidade {
    Integer id
    String sobrenome
    String cpf
    LocalDate dataNascimento
    String senha
    String telefone
    String linkedin

    boolean validarCpf() {
        return cpf?.replaceAll("[^0-9]", "")?.length() == 11
    }

    Integer getIdade() {
        if (!dataNascimento) return null
        Period.between(dataNascimento, LocalDate.now()).years
    }

    @Override
    String toString() {
        String dt = dataNascimento ? dataNascimento.format(DateTimeFormatter.ISO_DATE) : ""
        """
        ID: ${id}
        Nome: ${nome} ${sobrenome ?: ''}
        Email: ${email}
        CPF: ${cpf}
        Idade: ${getIdade()}
        Data Nascimento: ${dt}
        Estado: ${estado}
        CEP: ${cep}
        Descricao: ${descricao}
        Competencias: ${competencias.join(", ")}
        """.stripIndent() // Remove a indentacao das Strings multi-line
    }
}
