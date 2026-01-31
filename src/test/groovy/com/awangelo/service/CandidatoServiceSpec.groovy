package com.awangelo.service

import com.awangelo.model.Candidato
import com.awangelo.model.Competencia
import spock.lang.Specification

class CandidatoServiceSpec extends Specification {

    def "Deve adicionar novo candidato aos existentes"() {
        given: "Uma lista inicial de candidatos e um novo candidato"
        def service = new CandidatoService()
        def tamanhoInicial = service.listarTodos().size()
        def novoCandidato = new Candidato(
            nome: "Novo Candidato",
            email: "novo@mail.com",
            cpf: "98765432100",
            idade: 30,
            estado: "RJ",
            cep: "20040020",
            descricao: "Novo candidato teste",
            competencias: [Competencia.PYTHON]
        )

        when: "Eh adicionado o novo candidato"
        service.adicionar(novoCandidato)

        then: "O novo candidato esta na lista de candidatos"
        service.listarTodos().size() == tamanhoInicial + 1
        novoCandidato.id != null
        service.listarTodos().contains(novoCandidato)
    }
}
