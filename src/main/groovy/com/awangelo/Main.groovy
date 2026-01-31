package com.awangelo

import com.awangelo.model.Candidato
import com.awangelo.model.Empresa
import com.awangelo.model.Vaga
import com.awangelo.model.Curtida
import com.awangelo.model.Competencia
import com.awangelo.service.CandidatoService
import com.awangelo.service.EmpresaService
import com.awangelo.service.VagaService
import com.awangelo.service.CurtidaService

class Main {
    static CandidatoService candidatoService = new CandidatoService()
    static EmpresaService empresaService = new EmpresaService()
    static VagaService vagaService = new VagaService(empresaService)
    static CurtidaService curtidaService = new CurtidaService()
    static Scanner scanner = new Scanner(System.in)

    static void main() {
        int opcao = 0

        while (opcao != 9) {
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
                    listarVagas()
                    break
                case 4:
                    cadastrarCandidato()
                    break
                case 5:
                    cadastrarEmpresa()
                    break
                case 6:
                    cadastrarVaga()
                    break
                case 7:
                    curtirVaga()
                    break
                case 8:
                    curtirCandidato()
                    break
                case 9:
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
        println "3. Listar Vagas"
        println "4. Cadastrar Candidato"
        println "5. Cadastrar Empresa"
        println "6. Cadastrar Vaga"
        println "7. Curtir Vaga (Candidato)"
        println "8. Curtir Candidato (Empresa)"
        println "9. Sair"
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

    static void listarVagas() {
        println "\n=== VAGAS ==="
        vagaService.listarTodos().each { vaga ->
            println "\n--- Vaga ${vaga.id} ---"
            println vaga
        }
    }

    static void cadastrarVaga() {
        println "\n=== CADASTRAR VAGA ==="

        println "\nSelecione a empresa:"
        empresaService.listarTodos().each { empresa ->
            println "${empresa.id}. ${empresa.nome}"
        }
        print "ID da Empresa: "
        Integer empresaId = lerOpcao()
        Empresa empresa = empresaService.buscarPorId(empresaId)

        if (!empresa) {
            println "Empresa nao encontrada!"
            return
        }

        print "Nome da vaga: "
        String nome = scanner.nextLine()

        print "Descricao: "
        String descricao = scanner.nextLine()

        print "Local (estado): "
        String local = scanner.nextLine()

        List<Competencia> competencias = selecionarCompetencias()

        Vaga vaga = new Vaga(
            nome: nome,
            descricao: descricao,
            local: local,
            empresa: empresa,
            competencias: competencias
        )

        vagaService.adicionar(vaga)
        println "\nVaga cadastrada com sucesso!"
    }

    static void curtirVaga() {
        println "\n=== CURTIR VAGA (CANDIDATO) ==="

        println "\nSelecione o candidato:"
        candidatoService.listarTodos().each { candidato ->
            println "${candidato.id}. ${candidato.nome}"
        }
        print "ID do Candidato: "
        Integer candidatoId = lerOpcao()
        Candidato candidato = candidatoService.buscarPorId(candidatoId)

        if (!candidato) {
            println "Candidato nao encontrado!"
            return
        }

        println "\nVagas disponiveis:"
        vagaService.listarTodos().each { vaga ->
            println "${vaga.id}. ${vaga.nome} - ${vaga.empresa?.nome}"
        }
        print "ID da Vaga: "
        Integer vagaId = lerOpcao()
        Vaga vaga = vagaService.buscarPorId(vagaId)

        if (!vaga) {
            println "Vaga nao encontrada!"
            return
        }

        Curtida curtida = curtidaService.candidatoCurteVaga(candidato, vaga)
        println "\nCurtida registrada!"
        if (curtida.isMatch()) {
            println "*** MATCH! ${candidato.nome} e ${vaga.empresa?.nome} deram match! ***"
        }
    }

    static void curtirCandidato() {
        println "\n=== CURTIR CANDIDATO (EMPRESA) ==="

        println "\nSelecione a empresa:"
        empresaService.listarTodos().each { empresa ->
            println "${empresa.id}. ${empresa.nome}"
        }
        print "ID da Empresa: "
        Integer empresaId = lerOpcao()
        Empresa empresa = empresaService.buscarPorId(empresaId)

        if (!empresa) {
            println "Empresa nao encontrada!"
            return
        }

        List<Vaga> vagasDaEmpresa = vagaService.buscarPorEmpresa(empresa)
        if (vagasDaEmpresa.isEmpty()) {
            println "Esta empresa nao possui vagas cadastradas!"
            return
        }

        println "\nSelecione a vaga:"
        vagasDaEmpresa.each { vaga ->
            println "${vaga.id}. ${vaga.nome}"
        }
        print "ID da Vaga: "
        Integer vagaId = lerOpcao()
        Vaga vaga = vagaService.buscarPorId(vagaId)

        if (!vaga || vaga.empresa?.id != empresa.id) {
            println "Vaga nao encontrada ou nao pertence a esta empresa!"
            return
        }

        println "\nCandidatos disponiveis:"
        candidatoService.listarTodos().each { candidato ->
            println "${candidato.id}. ${candidato.nome} - ${candidato.competencias.join(', ')}"
        }
        print "ID do Candidato: "
        Integer candidatoId = lerOpcao()
        Candidato candidato = candidatoService.buscarPorId(candidatoId)

        if (!candidato) {
            println "Candidato nao encontrado!"
            return
        }

        Curtida curtida = curtidaService.empresaCurteCandidato(empresa, candidato, vaga)
        println "\nCurtida registrada!"
        if (curtida.isMatch()) {
            println "*** MATCH! ${candidato.nome} e ${empresa.nome} deram match! ***"
        }
    }
}