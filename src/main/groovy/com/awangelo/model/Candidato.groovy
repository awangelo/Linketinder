package com.awangelo.model

class Candidato implements Entidade {
    String cpf
    Integer idade

    boolean validarCpf() {
        return cpf?.replaceAll("[^0-9]", "")?.length() == 11
    }

    @Override
    String toString() {
        return """
            Nome: ${nome}
            Email: ${email}
            CPF: ${cpf}
            Idade: ${idade}
            Estado: ${estado}
            CEP: ${cep}
            Descricao: ${descricao}
            Competencias: ${competencias.join(", ")}
            """.stripIndent() // Remove a indentacao das Strings multi-line
    }
}
