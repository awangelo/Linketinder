package com.awangelo.service

import com.awangelo.model.Candidato
import com.awangelo.model.Competencia

class CandidatoService {
    private List<Candidato> candidatos = []

    CandidatoService() {
        carregarDadosIniciais()
    }

    private void carregarDadosIniciais() {
        candidatos = [
            new Candidato(
                nome: "Alice Liddell",
                email: "alice.liddell@mail.com",
                cpf: "12345678901",
                idade: 28,
                estado: "SP",
                cep: "01310103",
                descricao: "Desenvolvedora backend com 3 anos de experiencia",
                competencias: [Competencia.JAVA, Competencia.SPRING_FRAMEWORK, Competencia.SQL]
            ),
            new Candidato(
                nome: "Joao Pereira",
                email: "joao.pereira@mail.com",
                cpf: "23456789012",
                idade: 32,
                estado: "RJ",
                cep: "20240420",
                descricao: "Fullstack developer focado em aplicacoes web",
                competencias: [Competencia.JAVASCRIPT, Competencia.REACT, Competencia.MONGODB]
            ),
            new Candidato(
                nome: "Felipe Silva",
                email: "felipe.silva@mail.com",
                cpf: "34567890123",
                idade: 25,
                estado: "MG",
                cep: "30130330",
                descricao: "Engenheira de dados com conhecimento em cloud",
                competencias: [Competencia.PYTHON, Competencia.SQL, Competencia.AWS]
            ),
            new Candidato(
                nome: "Matheus",
                email: "matheus@mail.com",
                cpf: "45678901234",
                idade: 30,
                estado: "DF",
                cep: "40010404",
                descricao: "Desenvolvedor Groovy apaixonado por automacao",
                competencias: [Competencia.GROOVY, Competencia.JAVA, Competencia.SPRING_FRAMEWORK]
            ),
            new Candidato(
                nome: "Pedro",
                email: "pedro@mail.com",
                cpf: "56789012345",
                idade: 27,
                estado: "SC",
                cep: "58050050",
                descricao: "Frontend developer com foco em UX",
                competencias: [Competencia.JAVASCRIPT, Competencia.ANGULAR, Competencia.REACT]
            )
        ]
    }

    List<Candidato> listarTodos() {
        return candidatos
    }

    void adicionar(Candidato candidato) {
        candidatos.add(candidato)
    }
}
