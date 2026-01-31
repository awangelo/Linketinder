package com.awangelo.model

trait Entidade {
    String nome
    String email
    String estado
    String cep
    String descricao
    List<Competencia> competencias = []

    boolean validarEmail() {
        return email?.contains("@") && email?.contains(".")
    }

    boolean validarCep() {
        return cep?.replaceAll("[^0-9]", "")?.length() == 8
    }
}
