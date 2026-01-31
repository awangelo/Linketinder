package com.awangelo

import com.awangelo.model.Candidato
import com.awangelo.model.Empresa
import com.awangelo.model.Competencia
import com.awangelo.service.CandidatoService
import com.awangelo.service.EmpresaService

class Main {
    static CandidatoService candidatoService = new CandidatoService()
    static EmpresaService empresaService = new EmpresaService()
    static Scanner scanner = new Scanner(System.in)

    static void main() {
        int opcao = 0

        while (opcao != 5) {
            exibirMenu()
            opcao = lerOpcao()

            switch (opcao) {
                case 1:
                    listarCandidatos()
                    break
                case 2:
                    listarEmpresas()
                    break
                case 3:
                    cadastrarCandidato()
                    break
                case 4:
                    cadastrarEmpresa()
                    break
                case 5:
                    println "Saindo..."
                    break
                default:
                    println "Opcao invalida!"
            }
        }
    }

    static void exibirMenu() {
        println "\n=== LINKETINDER ==="
        println "1. Listar Candidatos"
        println "2. Listar Empresas"
        println "3. Cadastrar Candidato"
        println "4. Cadastrar Empresa"
        println "5. Sair"
        print "Escolha uma opcao: "
    }

    static int lerOpcao() {
        try {
            return scanner.nextLine().toInteger()
        } catch (NumberFormatException e) {
            return -1
        }
    }

    static void listarCandidatos() {
        println "\n=== CANDIDATOS ==="
        candidatoService.listarTodos().eachWithIndex { candidato, index ->
            println "\n--- Candidato ${index + 1} ---"
            println candidato
        }
    }

    static void listarEmpresas() {
        println "\n=== EMPRESAS ==="
        empresaService.listarTodos().eachWithIndex { empresa, index ->
            println "\n--- Empresa ${index + 1} ---"
            println empresa
        }
    }

    static void cadastrarCandidato() {
        println "\n=== CADASTRAR CANDIDATO ==="

        print "Nome: "
        String nome = scanner.nextLine()

        print "Email: "
        String email = scanner.nextLine()

        print "CPF (apenas numeros): "
        String cpf = scanner.nextLine()

        print "Idade: "
        Integer idade = scanner.nextLine().toInteger()

        print "Estado (sigla): "
        String estado = scanner.nextLine()

        print "CEP (apenas numeros): "
        String cep = scanner.nextLine()

        print "Descricao pessoal: "
        String descricao = scanner.nextLine()

        List<Competencia> competencias = selecionarCompetencias()

        Candidato candidato = new Candidato(
            nome: nome,
            email: email,
            cpf: cpf,
            idade: idade,
            estado: estado,
            cep: cep,
            descricao: descricao,
            competencias: competencias
        )

        candidatoService.adicionar(candidato)
        println "\nCandidato cadastrado com sucesso!"
    }

    static void cadastrarEmpresa() {
        println "\n=== CADASTRAR EMPRESA ==="

        print "Nome: "
        String nome = scanner.nextLine()

        print "Email corporativo: "
        String email = scanner.nextLine()

        print "CNPJ (apenas numeros): "
        String cnpj = scanner.nextLine()

        print "Pais: "
        String pais = scanner.nextLine()

        print "Estado (sigla): "
        String estado = scanner.nextLine()

        print "CEP (apenas numeros): "
        String cep = scanner.nextLine()

        print "Descricao da empresa: "
        String descricao = scanner.nextLine()

        List<Competencia> competencias = selecionarCompetencias()

        Empresa empresa = new Empresa(
            nome: nome,
            email: email,
            cnpj: cnpj,
            pais: pais,
            estado: estado,
            cep: cep,
            descricao: descricao,
            competencias: competencias
        )

        empresaService.adicionar(empresa)
        println "\nEmpresa cadastrada com sucesso!"
    }

    static List<Competencia> selecionarCompetencias() {
        println "\nCompetencias disponiveis:"
        Competencia.values().eachWithIndex { comp, index ->
            println "${index + 1}. ${comp}"
        }

        print "Digite os numeros das competencias separados por virgula: "
        String input = scanner.nextLine()

        List<Competencia> selecionadas = []
        input.split(",").each { num ->
            try {
                int index = num.trim().toInteger() - 1
                if (index >= 0 && index < Competencia.values().length) {
                    selecionadas.add(Competencia.values()[index])
                }
            } catch (NumberFormatException e) {
                // ignora entrada invalida
            }
        }

        return selecionadas
    }
}