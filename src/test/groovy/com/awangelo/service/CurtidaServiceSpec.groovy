package com.awangelo.service

import com.awangelo.dao.ICurtidaDAO
import com.awangelo.model.Curtida
import com.awangelo.model.Candidato
import com.awangelo.model.Empresa
import com.awangelo.model.Vaga
import spock.lang.Specification

class CurtidaServiceSpec extends Specification {

    ICurtidaDAO curtidaDAO = Mock()

    CurtidaService service = new CurtidaService(curtidaDAO)

    def 'Deve registrar curtida de candidato em vaga sem match'() {
        given: 'Um candidato e uma vaga'
        def candidato = new Candidato(id: 1, nome: 'Joao')
        def vaga = new Vaga(id: 10, nome: 'Vaga Java')

        when: 'Candidato curte a vaga'
        def resultado = service.candidatoCurteVaga(candidato, vaga)

        then: 'DAO.inserirCurtida eh chamado e retorna sem match'
        1 * curtidaDAO.inserirCurtida(1, 10, 'CANDIDATO') >> [id: 100, isMatch: false]

        and: 'Curtida eh criada com origem CANDIDATO'
        resultado.id == 100
        resultado.candidato == candidato
        resultado.vaga == vaga
        resultado.origemCurtida == 'CANDIDATO'
    }

    def 'Deve registrar curtida de candidato em vaga com match'() {
        given: 'Um candidato e uma vaga onde empresa ja curtiu'
        def candidato = new Candidato(id: 2, nome: 'Maria')
        def vaga = new Vaga(id: 20, nome: 'Vaga Python')

        when: 'Candidato curte a vaga'
        def resultado = service.candidatoCurteVaga(candidato, vaga)

        then: 'DAO.inserirCurtida retorna com match'
        1 * curtidaDAO.inserirCurtida(2, 20, 'CANDIDATO') >> [id: 200, isMatch: true]

        and: 'Curtida eh marcada como MATCH'
        resultado.id == 200
        resultado.origemCurtida == 'MATCH'
    }

    def 'Deve lancar excecao ao curtir com candidato invalido'() {
        given: 'Um candidato sem ID'
        def candidato = new Candidato(nome: 'Sem ID')
        def vaga = new Vaga(id: 10, nome: 'Vaga')

        when: 'Tenta curtir'
        service.candidatoCurteVaga(candidato, vaga)

        then: 'Excecao eh lancada'
        thrown(IllegalArgumentException)
    }

    def 'Deve lancar excecao ao curtir com vaga invalida'() {
        given: 'Uma vaga sem ID'
        def candidato = new Candidato(id: 1, nome: 'Joao')
        def vaga = new Vaga(nome: 'Sem ID')

        when: 'Tenta curtir'
        service.candidatoCurteVaga(candidato, vaga)

        then: 'Excecao eh lancada'
        thrown(IllegalArgumentException)
    }

    def 'Deve registrar curtida de empresa em candidato sem match'() {
        given: 'Uma empresa, candidato e vaga'
        def empresa = new Empresa(id: 5, nome: 'Tech Corp')
        def candidato = new Candidato(id: 3, nome: 'Pedro')
        def vaga = new Vaga(id: 30, nome: 'Vaga Groovy')

        when: 'Empresa curte o candidato'
        def resultado = service.empresaCurteCandidato(empresa, candidato, vaga)

        then: 'DAO.inserirCurtida eh chamado com origem EMPRESA'
        1 * curtidaDAO.inserirCurtida(3, 30, 'EMPRESA') >> [id: 300, isMatch: false]

        and: 'Curtida eh criada com origem EMPRESA'
        resultado.id == 300
        resultado.origemCurtida == 'EMPRESA'
    }

    def 'Deve registrar curtida de empresa em candidato com match'() {
        given: 'Uma empresa, candidato e vaga onde candidato ja curtiu'
        def empresa = new Empresa(id: 6, nome: 'Dev Inc')
        def candidato = new Candidato(id: 4, nome: 'Ana')
        def vaga = new Vaga(id: 40, nome: 'Vaga React')

        when: 'Empresa curte o candidato'
        def resultado = service.empresaCurteCandidato(empresa, candidato, vaga)

        then: 'DAO.inserirCurtida retorna com match'
        1 * curtidaDAO.inserirCurtida(4, 40, 'EMPRESA') >> [id: 400, isMatch: true]

        and: 'Curtida eh marcada como MATCH'
        resultado.id == 400
        resultado.origemCurtida == 'MATCH'
    }

    def 'Deve listar todas as curtidas'() {
        given: 'Uma lista de curtidas no DAO'
        def curtidas = [
                new Curtida(id: 1, origemCurtida: 'CANDIDATO'),
                new Curtida(id: 2, origemCurtida: 'EMPRESA')
        ]

        when: 'Lista todas as curtidas'
        def resultado = service.listarTodos()

        then: 'DAO.listarTodos eh chamado'
        1 * curtidaDAO.listarTodos() >> curtidas

        and: 'Retorna a lista correta'
        resultado == curtidas
        resultado.size() == 2
    }

    def 'Deve listar apenas matches'() {
        given: 'Uma lista de matches no DAO'
        def matches = [
                new Curtida(id: 1, origemCurtida: 'MATCH'),
                new Curtida(id: 2, origemCurtida: 'MATCH')
        ]

        when: 'Lista os matches'
        def resultado = service.listarMatches()

        then: 'DAO.listarMatches eh chamado'
        1 * curtidaDAO.listarMatches() >> matches

        and: 'Retorna apenas matches'
        resultado == matches
        resultado.size() == 2
    }

    def 'Deve listar curtidas por empresa'() {
        given: 'Uma empresa e suas curtidas'
        def empresa = new Empresa(id: 7, nome: 'Startup XYZ')
        def curtidas = [
                new Curtida(id: 1, origemCurtida: 'EMPRESA'),
                new Curtida(id: 2, origemCurtida: 'CANDIDATO')
        ]

        when: 'Lista curtidas da empresa'
        def resultado = service.listarCurtidasPorEmpresa(empresa)

        then: 'DAO.listarCurtidasPorEmpresa eh chamado com ID correto'
        1 * curtidaDAO.listarCurtidasPorEmpresa(7) >> curtidas

        and: 'Retorna as curtidas da empresa'
        resultado == curtidas
        resultado.size() == 2
    }

    def 'Deve listar curtidas por candidato'() {
        given: 'Um candidato e suas curtidas'
        def candidato = new Candidato(id: 8, nome: 'Lucas')
        def curtidas = [
                new Curtida(id: 1, origemCurtida: 'CANDIDATO'),
                new Curtida(id: 2, origemCurtida: 'EMPRESA')
        ]

        when: 'Lista curtidas do candidato'
        def resultado = service.listarCurtidasPorCandidato(candidato)

        then: 'DAO.listarCurtidasPorCandidato eh chamado com ID correto'
        1 * curtidaDAO.listarCurtidasPorCandidato(8) >> curtidas

        and: 'Retorna as curtidas do candidato'
        resultado == curtidas
        resultado.size() == 2
    }
}

