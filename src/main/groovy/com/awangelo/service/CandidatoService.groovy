package com.awangelo.service

import com.awangelo.model.Candidato
import com.awangelo.model.Competencia

class CandidatoService {
    private List<Candidato> candidatos = []
    private Integer proximoId = 1

    CandidatoService() {
        carregarDadosIniciais()
    }

    private void carregarDadosIniciais() {
        candidatos = [
            new Candidato(
                id: proximoId++,
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
                id: proximoId++,
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
                id: proximoId++,
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
                id: proximoId++,
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
                id: proximoId++,
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

    Candidato buscarPorId(Integer id) {
        return candidatos.find { it.id == id }
    }

    void adicionar(Candidato candidato) {
        candidato.id = proximoId++
        candidatos.add(candidato)
    }
}
