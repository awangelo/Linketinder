package com.awangelo.model

enum Competencia {
    JAVA("Java"),
    GROOVY("Groovy"),
    PYTHON("Python"),
    JAVASCRIPT("JavaScript"),
    SPRING_FRAMEWORK("Spring Framework"),
    ANGULAR("Angular"),
    REACT("React"),
    SQL("SQL"),
    MONGODB("MongoDB"),
    AWS("AWS")

    String descricao

    Competencia(String descricao) {
        this.descricao = descricao
    }

    @Override
    String toString() {
        return descricao
    }
}
