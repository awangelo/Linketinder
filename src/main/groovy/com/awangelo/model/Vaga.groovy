package com.awangelo.model

class Vaga {
    Integer id
    String nome
    String descricao
    String localVaga
    Empresa empresa
    List<Competencia> competencias = []

    @Override
    String toString() {
        """
        ID: ${id}
        Nome: ${nome}
        Descricao: ${descricao}
        Local: ${localVaga}
        Empresa: ${empresa?.nome}
        Competencias: ${competencias.join(", ")}
        """.stripIndent()
    }
}
