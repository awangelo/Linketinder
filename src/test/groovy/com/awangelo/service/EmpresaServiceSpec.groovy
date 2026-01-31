package com.awangelo.service

import com.awangelo.model.Empresa
import com.awangelo.model.Competencia
import spock.lang.Specification

class EmpresaServiceSpec extends Specification {

    def "Deve adicionar nova empresa as existentes"() {
        given: "Uma lista inicial de empresas e uma nova empresa"
        def service = new EmpresaService()
        def tamanhoInicial = service.listarTodos().size()
        def novaEmpresa = new Empresa(
            nome: "Nova Empresa",
            email: "contato@novaempresa.com",
            cnpj: "98765432000188",
            pais: "Brasil",
            estado: "MG",
            cep: "30130000",
            descricao: "Nova empresa teste",
            competencias: [Competencia.AWS]
        )

        when: "Eh adicionada a nova empresa"
        service.adicionar(novaEmpresa)

        then: "A nova empresa esta na lista de empresas"
        service.listarTodos().size() == tamanhoInicial + 1
        novaEmpresa.id != null
        service.listarTodos().contains(novaEmpresa)
    }
}
