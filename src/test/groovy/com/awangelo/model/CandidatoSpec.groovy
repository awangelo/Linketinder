package com.awangelo.model

import spock.lang.Specification

class CandidatoSpec extends Specification {

    def "Deve criar candidato com todos os atributos"() {
        given:
        def competencias = [Competencia.JAVA, Competencia.GROOVY]

        when:
        def candidato = new Candidato(
            id: 1,
            nome: "Teste",
            email: "teste@mail.com",
            cpf: "12345678901",
            idade: 25,
            estado: "SP",
            cep: "01310100",
            descricao: "Descricao teste",
            competencias: competencias
        )

        then:
        candidato.id == 1
        candidato.nome == "Teste"
        candidato.email == "teste@mail.com"
        candidato.cpf == "12345678901"
        candidato.idade == 25
        candidato.estado == "SP"
        candidato.cep == "01310100"
        candidato.descricao == "Descricao teste"
        candidato.competencias == competencias
    }
}
