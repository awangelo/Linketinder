package com.awangelo.model

class Empresa implements Entidade {
    Integer id
    String cnpj
    String senha

    boolean validarCnpj() {
        cnpj?.replaceAll("[^0-9]", "")?.length() == 14
    }

    @Override
    String toString() {
        """
        ID: ${id}
        Nome: ${nome}
        Email Corporativo: ${email}
        CNPJ: ${cnpj}
        Pais: ${pais}
        Estado: ${estado}
        CEP: ${cep}
        Descricao: ${descricao}
        """.stripIndent()
    }
}
