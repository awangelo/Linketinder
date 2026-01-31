package com.awangelo.service

import com.awangelo.model.Empresa
import com.awangelo.model.Competencia

class EmpresaService {
    private List<Empresa> empresas = []
    private Integer proximoId = 1

    EmpresaService() {
        carregarDadosIniciais()
    }

    private void carregarDadosIniciais() {
        empresas = [
            new Empresa(
                id: proximoId++,
                nome: "Arroz Gostoso",
                email: "rh@arrozgostoso.com.br",
                cnpj: "12345678000111",
                pais: "Brasil",
                estado: "SP",
                cep: "01310100",
                descricao: "Empresa lider no setor alimenticio",
                competencias: [Competencia.JAVA, Competencia.SPRING_FRAMEWORK, Competencia.SQL]
            ),
            new Empresa(
                id: proximoId++,
                nome: "Imperio do Boliche",
                email: "contato@imperioboliche.com.br",
                cnpj: "23456789000222",
                pais: "Brasil",
                estado: "RJ",
                cep: "20040020",
                descricao: "Maior rede de boliches do Brasil",
                competencias: [Competencia.JAVASCRIPT, Competencia.REACT, Competencia.MONGODB]
            ),
            new Empresa(
                id: proximoId++,
                nome: "Brocrosoft",
                email: "rh@brocrosoft.com",
                cnpj: "34567890000333",
                pais: "Brasil",
                estado: "MG",
                cep: "30130000",
                descricao: "Consultoria em transformacao digital",
                competencias: [Competencia.PYTHON, Competencia.AWS, Competencia.SQL]
            ),
            new Empresa(
                id: proximoId++,
                nome: "DevMaster",
                email: "sandubinha@devmaster.com",
                cnpj: "45678901000444",
                pais: "Brasil",
                estado: "PR",
                cep: "80010000",
                descricao: "Fabrica de software especializada em Groovy",
                competencias: [Competencia.GROOVY, Competencia.JAVA, Competencia.ANGULAR]
            ),
            new Empresa(
                id: proximoId++,
                nome: "Caixa",
                email: "contato@caixa.com",
                cnpj: "56789012000555",
                pais: "Brasil",
                estado: "SC",
                cep: "88010000",
                descricao: "Especialistas em solucoes bancarias",
                competencias: [Competencia.AWS, Competencia.PYTHON, Competencia.MONGODB]
            )
        ]
    }

    List<Empresa> listarTodos() {
        return empresas
    }

    Empresa buscarPorId(Integer id) {
        return empresas.find { it.id == id }
    }

    void adicionar(Empresa empresa) {
        empresa.id = proximoId++
        empresas.add(empresa)
    }
}
