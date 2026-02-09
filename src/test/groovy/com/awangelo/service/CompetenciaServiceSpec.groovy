package com.awangelo.service

import com.awangelo.dao.ICompetenciaDAO
import com.awangelo.model.Competencia
import spock.lang.Specification

class CompetenciaServiceSpec extends Specification {

    ICompetenciaDAO competenciaDAO = Mock()

    CompetenciaService service = new CompetenciaService(competenciaDAO)

    def 'Deve listar todas as competencias do DAO'() {
        given: 'Uma lista de competencias no DAO'
        ArrayList<Competencia> competencias = [Competencia.JAVA, Competencia.PYTHON, Competencia.GROOVY]

        when: 'Lista todas as competencias'
        List<Competencia> resultado = service.listarTodos()

        then: 'DAO.listarTodos eh chamado'
        1 * competenciaDAO.listarTodos() >> competencias

        and: 'Retorna a lista correta'
        resultado == competencias
        resultado.size() == 3
    }

    def 'Deve adicionar competencia e retornar ID'() {
        given: 'Uma competencia a ser adicionada'
        Competencia competencia = Competencia.JAVA

        when: 'Adiciona a competencia'
        Integer resultado = service.adicionar(competencia)

        then: 'DAO.getIdOrCreate eh chamado e retorna ID'
        1 * competenciaDAO.getIdOrCreate(competencia) >> 42

        and: 'Retorna o ID correto'
        resultado == 42
    }

    def 'Deve renomear competencia existente'() {
        given: 'Nomes antigo e novo'
        String nomeAntigo = 'OLD_NAME'
        String nomeNovo = 'NEW_NAME'

        when: 'Renomeia a competencia'
        Integer resultado = service.renomear(nomeAntigo, nomeNovo)

        then: 'DAO.update eh chamado e retorna ID'
        1 * competenciaDAO.update(nomeAntigo, nomeNovo) >> 5

        and: 'Retorna o ID correto'
        resultado == 5
    }

    def 'Deve remover competencia por nome com sucesso'() {
        given: 'Um nome de competencia'
        String nome = 'JAVA'

        when: 'Remove a competencia'
        boolean resultado = service.remover(nome)

        then: 'DAO.deleteByName eh chamado e retorna true'
        1 * competenciaDAO.deleteByName(nome) >> true

        and: 'Retorna true'
        resultado
    }

    def 'Deve retornar false quando competencia nao existe para remocao'() {
        given: 'Um nome de competencia inexistente'
        String nome = 'NONEXISTENT'

        when: 'Tenta remover a competencia'
        boolean resultado = service.remover(nome)

        then: 'DAO.deleteByName retorna false'
        1 * competenciaDAO.deleteByName(nome) >> false

        and: 'Retorna false'
        !resultado
    }
}

