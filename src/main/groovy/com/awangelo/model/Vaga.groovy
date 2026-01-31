package com.awangelo.model

class Vaga {
    Integer id
    String nome
    String descricao
    String local
    Empresa empresa
    List<Competencia> competencias = []

    @Override
    String toString() {
        return """
            ID: ${id}
            Nome: ${nome}
            Descricao: ${descricao}
            Local: ${local}
            Empresa: ${empresa?.nome}
            Competencias: ${competencias.join(", ")}
            """.stripIndent()
    }
}
