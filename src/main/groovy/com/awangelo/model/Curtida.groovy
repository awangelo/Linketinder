package com.awangelo.model

class Curtida {
    Integer id
    Candidato candidato
    Vaga vaga
    Empresa empresa

    boolean isMatch() {
        return candidato != null && empresa != null
    }

    @Override
    String toString() {
        return """
            Candidato: ${candidato?.nome}
            Vaga: ${vaga?.nome}
            Empresa: ${empresa?.nome}
            Match: ${isMatch() ? "Sim" : "Nao"}
            """.stripIndent()
    }
}
