package com.awangelo.model

class Curtida {
    Integer id
    Candidato candidato
    Vaga vaga
    String origemCurtida  // 'CANDIDATO' ou 'EMPRESA'

    boolean isMatch() {
        // Match ocorre quando existe curtida do candidato E da empresa para mesma vaga.
        // Verificado no DAO/Service.
        return candidato != null && vaga != null
    }

    @Override
    String toString() {
        return """
            ID: ${id}
            Candidato: ${candidato?.nome}
            Vaga: ${vaga?.nome}
            Empresa: ${vaga?.empresa?.nome}
            Origem: ${origemCurtida}
            """.stripIndent()
    }
}
