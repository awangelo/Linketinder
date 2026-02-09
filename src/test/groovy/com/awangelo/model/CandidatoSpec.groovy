package com.awangelo.model

import spock.lang.Specification

import java.time.LocalDate

class CandidatoSpec extends Specification {

    def 'Deve criar candidato com todos os atributos'() {
        given:
        def competencias = [Competencia.JAVA, Competencia.GROOVY]

        when:
        def candidato = new Candidato(
            id: 1,
            nome: 'Teste',
            sobrenome: 'Sobrenome',
            email: 'teste@mail.com',
            cpf: '12345678901',
            dataNascimento: LocalDate.parse('2007-12-03'),
            pais: 'Brasil',
            estado: 'SP',
            cep: '01310100',
            descricao: 'Descricao teste',
            competencias: competencias,
            senha: '123456',
            telefone: '61987658765',
            linkedin: 'https://www.linkedin.com/in/foo/'
        )

        then:
        candidato.id == 1
        candidato.nome == 'Teste'
        candidato.sobrenome == 'Sobrenome'
        candidato.email == 'teste@mail.com'
        candidato.cpf == '12345678901'
        candidato.dataNascimento == LocalDate.parse('2007-12-03')
        candidato.pais == 'Brasil'
        candidato.estado == 'SP'
        candidato.cep == '01310100'
        candidato.descricao == 'Descricao teste'
        candidato.competencias == competencias
        candidato.senha == '123456'
        candidato.telefone == '61987658765'
        candidato.linkedin == 'https://www.linkedin.com/in/foo/'
    }
}
