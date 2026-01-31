package com.awangelo.model

class Empresa implements Entidade {
    Integer id
    String cnpj
    String pais

    boolean validarCnpj() {
        return cnpj?.replaceAll("[^0-9]", "")?.length() == 14
    }

    @Override
    String toString() {
        return """
            ID: ${id}
            Nome: ${nome}
            Email Corporativo: ${email}
            CNPJ: ${cnpj}
            Pais: ${pais}
            Estado: ${estado}
            CEP: ${cep}
            Descricao: ${descricao}
            Competencias desejadas: ${competencias.join(", ")}
            """.stripIndent() // Remove a indentacao das Strings multi-line
    }
}
