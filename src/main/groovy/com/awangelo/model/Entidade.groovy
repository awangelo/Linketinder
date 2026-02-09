package com.awangelo.model

trait Entidade {
    String nome
    String email
    String pais
    String estado
    String cep
    String descricao
    List<Competencia> competencias = []

    boolean validarEmail() {
        email?.contains("@") && email?.contains(".")
    }

    boolean validarCep() {
        cep?.replaceAll("[^0-9]", "")?.length() == 8
    }
}
