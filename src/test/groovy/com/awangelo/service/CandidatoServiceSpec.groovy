package com.awangelo.service

import com.awangelo.dao.ICandidatoDAO
import com.awangelo.model.Candidato
import com.awangelo.model.Competencia
import spock.lang.Specification

import java.time.LocalDate

class CandidatoServiceSpec extends Specification {

    ICandidatoDAO candidatoDAO = Mock()

    CandidatoService service = new CandidatoService(candidatoDAO)

    def 'Deve adicionar candidato e atribuir ID retornado pelo DAO'() {
        given: 'Um novo candidato sem ID'
        Candidato candidato = new Candidato(
                nome: 'Novo Candidato',
                email: 'novo@mail.com',
                cpf: '98765432100',
                dataNascimento: LocalDate.parse('2007-12-03'),
                pais: 'Brasil',
                estado: 'SP',
                cep: '01310100',
                descricao: 'Descricao teste',
                senha: '123456',
                telefone: '61987658765',
                linkedin: 'https://www.linkedin.com/in/foo/',
                competencias: [Competencia.PYTHON]
        )

        when: 'Eh adicionado o novo candidato'
        service.adicionar(candidato)

        then: 'DAO.inserir eh chamado uma vez e retorna ID 42'
        1 * candidatoDAO.inserir(candidato) >> 42

        and: 'O ID eh atribuido ao candidato'
        candidato.id == 42
    }

    def 'Deve listar todos os candidatos do DAO'() {
        given: 'Uma lista de candidatos no DAO'
        ArrayList<Candidato> candidatos = [
                new Candidato(id: 1, nome: 'Joao Silva', email: 'joao@example.com'),
                new Candidato(id: 2, nome: 'Maria Santos', email: 'maria@example.com')
        ]

        when: 'Lista todos os candidatos'
        List<Candidato> resultado = service.listarTodos()

        then: 'DAO.listarTodos eh chamado'
        1 * candidatoDAO.listarTodos() >> candidatos

        and: 'Retorna a lista correta'
        resultado == candidatos
        resultado.size() == 2
    }

    def 'Deve buscar candidato por ID'() {
        given: 'Um candidato existente'
        Candidato candidato = new Candidato(id: 42, nome: 'Joao Silva', email: 'joao@example.com')

        when: 'Busca por ID'
        Candidato resultado = service.buscarPorId(42)

        then: 'DAO.buscarPorId eh chamado'
        1 * candidatoDAO.buscarPorId(42) >> candidato

        and: 'Retorna o candidato correto'
        resultado == candidato
        resultado.id == 42
    }

    def 'Deve atualizar candidato existente'() {
        given: 'Um candidato existente'
        Candidato candidato = new Candidato(id: 1, nome: 'Joao Atualizado', email: 'joao@example.com')

        when: 'Atualiza o candidato'
        service.atualizar(candidato)

        then: 'DAO.update eh chamado uma vez'
        1 * candidatoDAO.update(candidato)
    }

    def 'Deve remover candidato por ID com sucesso'() {
        when: 'Remove candidato'
        boolean resultado = service.remover(42)

        then: 'DAO.delete eh chamado e retorna true'
        1 * candidatoDAO.delete(42) >> true

        and: 'Retorna true'
        resultado
    }

    def 'Deve retornar false quando candidato nao existe para remocao'() {
        when: 'Tenta remover candidato inexistente'
        boolean resultado = service.remover(999)

        then: 'DAO.delete retorna false'
        1 * candidatoDAO.delete(999) >> false

        and: 'Retorna false'
        !resultado
    }
}
