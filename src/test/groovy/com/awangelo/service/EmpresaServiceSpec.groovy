package com.awangelo.service

import com.awangelo.dao.IEmpresaDAO
import com.awangelo.model.Empresa
import com.awangelo.model.Competencia
import spock.lang.Specification

class EmpresaServiceSpec extends Specification {

    IEmpresaDAO empresaDAO = Mock()

    EmpresaService service = new EmpresaService(empresaDAO)

    def 'Deve adicionar empresa e atribuir ID retornado pelo DAO'() {
        given: 'Uma nova empresa sem ID'
        def empresa = new Empresa(
                nome: 'Nova Empresa',
                email: 'contato@novaempresa.com',
                cnpj: '98765432000188',
                pais: 'Brasil',
                estado: 'MG',
                cep: '30130000',
                descricao: 'Nova empresa teste',
                competencias: [Competencia.AWS]
        )

        when: 'Eh adicionada a nova empresa'
        service.adicionar(empresa)

        then: 'DAO.inserir eh chamado uma vez e retorna ID 10'
        1 * empresaDAO.inserir(empresa) >> 10

        and: 'O ID eh atribuido a empresa'
        empresa.id == 10
    }

    def 'Deve listar todas as empresas do DAO'() {
        given: 'Uma lista de empresas no DAO'
        def empresas = [
                new Empresa(id: 1, nome: 'Empresa A', email: 'contato@a.com'),
                new Empresa(id: 2, nome: 'Empresa B', email: 'contato@b.com')
        ]

        when: 'Lista todas as empresas'
        def resultado = service.listarTodos()

        then: 'DAO.listarTodos eh chamado'
        1 * empresaDAO.listarTodos() >> empresas

        and: 'Retorna a lista correta'
        resultado == empresas
        resultado.size() == 2
    }

    def 'Deve buscar empresa por ID'() {
        given: 'Uma empresa existente'
        def empresa = new Empresa(id: 5, nome: 'Tech Corp', email: 'info@techcorp.com')

        when: 'Busca por ID'
        def resultado = service.buscarPorId(5)

        then: 'DAO.buscarPorId eh chamado'
        1 * empresaDAO.buscarPorId(5) >> empresa

        and: 'Retorna a empresa correta'
        resultado == empresa
        resultado.id == 5
    }

    def 'Deve atualizar empresa existente'() {
        given: 'Uma empresa existente'
        def empresa = new Empresa(id: 1, nome: 'Empresa Atualizada', email: 'novo@empresa.com')

        when: 'Atualiza a empresa'
        service.atualizar(empresa)

        then: 'DAO.update eh chamado uma vez'
        1 * empresaDAO.update(empresa)
    }

    def 'Deve remover empresa por ID com sucesso'() {
        when: 'Remove empresa'
        def resultado = service.remover(10)

        then: 'DAO.delete eh chamado e retorna true'
        1 * empresaDAO.delete(10) >> true

        and: 'Retorna true'
        resultado
    }

    def 'Deve retornar false quando empresa nao existe para remocao'() {
        when: 'Tenta remover empresa inexistente'
        def resultado = service.remover(999)

        then: 'DAO.delete retorna false'
        1 * empresaDAO.delete(999) >> false

        and: 'Retorna false'
        !resultado
    }
}
