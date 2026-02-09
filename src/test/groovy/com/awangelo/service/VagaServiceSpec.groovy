package com.awangelo.service

import com.awangelo.dao.IVagaDAO
import com.awangelo.model.Vaga
import com.awangelo.model.Empresa
import com.awangelo.model.Competencia
import spock.lang.Specification

class VagaServiceSpec extends Specification {

    IVagaDAO vagaDAO = Mock()

    VagaService service = new VagaService(vagaDAO)

    def 'Deve adicionar vaga e atribuir ID retornado pelo DAO'() {
        given: 'Uma nova vaga sem ID'
        def empresa = new Empresa(id: 1, nome: 'Tech Corp')
        def vaga = new Vaga(
                nome: 'Desenvolvedor Java',
                descricao: 'Vaga para desenvolvedor Java senior',
                localVaga: 'Remoto',
                empresa: empresa,
                competencias: [Competencia.JAVA, Competencia.SPRING_FRAMEWORK]
        )

        when: 'Eh adicionada a nova vaga'
        service.adicionar(vaga)

        then: 'DAO.inserir eh chamado uma vez e retorna ID 15'
        1 * vagaDAO.inserir(vaga) >> 15

        and: 'O ID eh atribuido a vaga'
        vaga.id == 15
    }

    def 'Deve listar todas as vagas do DAO'() {
        given: 'Uma lista de vagas no DAO'
        def empresa = new Empresa(id: 1, nome: 'Tech Corp')
        def vagas = [
                new Vaga(id: 1, nome: 'Vaga Java', empresa: empresa),
                new Vaga(id: 2, nome: 'Vaga Python', empresa: empresa)
        ]

        when: 'Lista todas as vagas'
        def resultado = service.listarTodos()

        then: 'DAO.listarTodos eh chamado'
        1 * vagaDAO.listarTodos() >> vagas

        and: 'Retorna a lista correta'
        resultado == vagas
        resultado.size() == 2
    }

    def 'Deve buscar vaga por ID'() {
        given: 'Uma vaga existente'
        def empresa = new Empresa(id: 1, nome: 'Tech Corp')
        def vaga = new Vaga(id: 10, nome: 'Desenvolvedor Groovy', empresa: empresa)

        when: 'Busca por ID'
        def resultado = service.buscarPorId(10)

        then: 'DAO.buscarPorId eh chamado'
        1 * vagaDAO.buscarPorId(10) >> vaga

        and: 'Retorna a vaga correta'
        resultado == vaga
        resultado.id == 10
    }

    def 'Deve atualizar vaga existente'() {
        given: 'Uma vaga existente'
        def empresa = new Empresa(id: 1, nome: 'Tech Corp')
        def vaga = new Vaga(id: 1, nome: 'Vaga Atualizada', empresa: empresa)

        when: 'Atualiza a vaga'
        service.atualizar(vaga)

        then: 'DAO.update eh chamado uma vez'
        1 * vagaDAO.update(vaga)
    }

    def 'Deve remover vaga por ID com sucesso'() {
        when: 'Remove vaga'
        def resultado = service.remover(20)

        then: 'DAO.delete eh chamado e retorna true'
        1 * vagaDAO.delete(20) >> true

        and: 'Retorna true'
        resultado
    }

    def 'Deve retornar false quando vaga nao existe para remocao'() {
        when: 'Tenta remover vaga inexistente'
        def resultado = service.remover(999)

        then: 'DAO.delete retorna false'
        1 * vagaDAO.delete(999) >> false

        and: 'Retorna false'
        !resultado
    }

    def 'Deve buscar vagas por empresa'() {
        given: 'Uma empresa e suas vagas'
        def empresa = new Empresa(id: 5, nome: 'Tech Corp')
        def todasVagas = [
                new Vaga(id: 1, nome: 'Vaga Java', empresa: empresa),
                new Vaga(id: 2, nome: 'Vaga Python', empresa: new Empresa(id: 10, nome: 'Outra')),
                new Vaga(id: 3, nome: 'Vaga Groovy', empresa: empresa)
        ]

        when: 'Busca vagas por empresa'
        def resultado = service.buscarPorEmpresa(empresa)

        then: 'DAO.listarTodos eh chamado'
        1 * vagaDAO.listarTodos() >> todasVagas

        and: 'Retorna apenas as vagas da empresa especificada'
        resultado.size() == 2
        resultado.every { it.empresa.id == 5 }
    }
}

