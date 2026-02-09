package com.awangelo.model

import spock.lang.Specification

class EmpresaSpec extends Specification {

    def 'Deve criar empresa com todos os atributos'() {
        given:
        def competencias = [Competencia.JAVA, Competencia.SPRING_FRAMEWORK]

        when:
        def empresa = new Empresa(
            id: 1,
            nome: 'Empresa Teste',
            email: 'contato@empresa.com',
            cnpj: '12345678000199',
            pais: 'Brasil',
            estado: 'SP',
            cep: '01310100',
            descricao: 'Descricao da empresa',
            competencias: competencias
        )

        then:
        empresa.id == 1
        empresa.nome == 'Empresa Teste'
        empresa.email == 'contato@empresa.com'
        empresa.cnpj == '12345678000199'
        empresa.pais == 'Brasil'
        empresa.estado == 'SP'
        empresa.cep == '01310100'
        empresa.descricao == 'Descricao da empresa'
        empresa.competencias == competencias
    }
}
