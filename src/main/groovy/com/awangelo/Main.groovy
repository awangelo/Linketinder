package com.awangelo

import com.awangelo.db.ConnectionFactory
import com.awangelo.model.Candidato
import com.awangelo.model.Empresa
import com.awangelo.model.Vaga
import com.awangelo.model.Curtida
import com.awangelo.model.Competencia
import com.awangelo.service.CandidatoService
import com.awangelo.service.EmpresaService
import com.awangelo.service.VagaService
import com.awangelo.service.CurtidaService
import com.awangelo.service.CompetenciaService
import java.time.LocalDate

class Main {
    static CandidatoService candidatoService = new CandidatoService()
    static EmpresaService empresaService = new EmpresaService()
    static VagaService vagaService = new VagaService()
    static CurtidaService curtidaService = new CurtidaService()
    static CompetenciaService competenciaService = new CompetenciaService()
    static Scanner scanner = new Scanner(System.in)

    static void main(String[] args) {
        int opcao = -1

        while (opcao != 0) {
            exibirMenuPrincipal()
            opcao = lerOpcao()

            switch (opcao) {
                case 1: menuCandidato(); break
                case 2: menuEmpresa(); break
                case 3: menuVaga(); break
                case 4: menuCompetencia(); break
                case 5: menuCurtida(); break
                case 0: println "Saindo..."; break
                default: println "Opcao invalida!"
            }
        }

        ConnectionFactory.close()
    }

    static void exibirMenuPrincipal() {
        println "\n=== LINKETINDER (MENU) ==="
        println "1. Candidatos (CRUD)"
        println "2. Empresas (CRUD)"
        println "3. Vagas (CRUD)"
        println "4. Competencias (CRUD)"
        println "5. Curtidas / Matchs"
        println "0. Sair"
        print "Escolha uma opcao: "
    }

    // ---------- CANDIDATO ----------
    static void menuCandidato() {
        println "\n--- CANDIDATOS ---"
        println "1. Listar"
        println "2. Cadastrar"
        println "3. Atualizar"
        println "4. Deletar"
        println "0. Voltar"
        print "Opcao: "
        switch (lerOpcao()) {
            case 1: listarCandidatos(); break
            case 2: cadastrarCandidato(); break
            case 3: atualizarCandidato(); break
            case 4: deletarCandidato(); break
            default: break
        }
    }

    static void listarCandidatos() {
        println "\n=== CANDIDATOS ==="
        candidatoService.listarTodos().eachWithIndex { c, i ->
            println "\n--- Candidato ${i+1} (ID: ${c.id}) ---"
            println c
        }
    }

    static void cadastrarCandidato() {
        println "\n=== CADASTRAR CANDIDATO ==="
        print "Nome: "; String nome = scanner.nextLine()
        print "Sobrenome: "; String sobrenome = scanner.nextLine()
        print "Email: "; String email = scanner.nextLine()
        print "CPF (apenas numeros): "; String cpf = scanner.nextLine()
        print "Data de Nascimento (yyyy-MM-dd): "; String dataStr = scanner.nextLine()
        LocalDate dataNascimento = null
        if (dataStr ==~ /\d{4}-\d{2}-\d{2}/) {
            try { dataNascimento = LocalDate.parse(dataStr) } catch (Exception ignored) {}
        }
        print "Pais: "; String pais = scanner.nextLine()
        print "Estado (sigla): "; String estado = scanner.nextLine()
        print "CEP (apenas numeros): "; String cep = scanner.nextLine()
        print "Descricao: "; String descricao = scanner.nextLine()
        print "Senha: "; String senha = scanner.nextLine()
        List<Competencia> comps = selecionarCompetencias()

        Candidato c = new Candidato(nome:nome, sobrenome:sobrenome, email:email, cpf:cpf, dataNascimento:dataNascimento, pais:pais, estado:estado, cep:cep, descricao:descricao, competencias:comps, senha:senha)
        try {
            candidatoService.adicionar(c)
            println "Candidato cadastrado (ID: ${c.id})"
        } catch (Exception e) {
            if (e.message?.contains('unique') || e.message?.contains('duplicate') || e.message?.contains('violates')) {
                println "\nErro: Email ou CPF ja cadastrado!"
            } else {
                println "\nErro ao cadastrar: ${e.message}"
            }
        }
    }

    static void atualizarCandidato() {
        print "ID do candidato a atualizar: "; Integer id = lerOpcao()
        def c = candidatoService.buscarPorId(id)
        if (!c) { println "Candidato nao encontrado"; return }
        print "Nome [${c.nome}]: "; String nome = scanner.nextLine(); if (nome) c.nome = nome
        print "Sobrenome [${c.sobrenome}]: "; String sobrenome = scanner.nextLine(); if (sobrenome) c.sobrenome = sobrenome
        print "Email [${c.email}]: "; String email = scanner.nextLine(); if (email) c.email = email
        print "CPF [${c.cpf}]: "; String cpf = scanner.nextLine(); if (cpf) c.cpf = cpf
        print "Data Nascimento (yyyy-MM-dd) [${c.dataNascimento}]: "; String ds = scanner.nextLine()
        if (ds ==~ /\d{4}-\d{2}-\d{2}/) { try { c.dataNascimento = LocalDate.parse(ds) } catch (Exception ignored) {} }
        print "Pais [${c.pais}]: "; String pais = scanner.nextLine(); if (pais) c.pais = pais
        print "Estado [${c.estado}]: "; String estado = scanner.nextLine(); if (estado) c.estado = estado
        print "CEP [${c.cep}]: "; String cep = scanner.nextLine(); if (cep) c.cep = cep
        print "Descricao [${c.descricao}]: "; String desc = scanner.nextLine(); if (desc) c.descricao = desc
        println "Competencias atuais: ${c.competencias.join(', ')}"
        print "Deseja atualizar competencias? (s/N): "; String up = scanner.nextLine()
        if (up?.toLowerCase() == 's') { c.competencias = selecionarCompetencias() }
        try {
            candidatoService.atualizar(c)
            println "Candidato atualizado"
        } catch (Exception e) {
            if (e.message?.contains('unique') || e.message?.contains('duplicate') || e.message?.contains('violates')) {
                println "\nErro: Email ou CPF ja cadastrado por outro candidato!"
            } else {
                println "\nErro ao atualizar: ${e.message}"
            }
        }
    }

    static void deletarCandidato() {
        print "ID do candidato a deletar: "; Integer id = lerOpcao()
        if (candidatoService.remover(id)) println "Candidato removido" else println "Nao foi possivel remover"
    }

    // ---------- EMPRESA ----------
    static void menuEmpresa() {
        println "\n--- EMPRESAS ---"
        println "1. Listar"
        println "2. Cadastrar"
        println "3. Atualizar"
        println "4. Deletar"
        println "0. Voltar"
        print "Opcao: "
        switch (lerOpcao()) {
            case 1: listarEmpresas(); break
            case 2: cadastrarEmpresa(); break
            case 3: atualizarEmpresa(); break
            case 4: deletarEmpresa(); break
            default: break
        }
    }

    static void listarEmpresas() {
        println "\n=== EMPRESAS ==="
        empresaService.listarTodos().eachWithIndex { e, i -> println "\n--- Empresa ${i+1} (ID: ${e.id}) ---"; println e }
    }

    static void cadastrarEmpresa() {
        println "\n=== CADASTRAR EMPRESA ==="
        print "Nome: "; String nome = scanner.nextLine()
        print "Email: "; String email = scanner.nextLine()
        print "CNPJ: "; String cnpj = scanner.nextLine()
        print "Pais: "; String pais = scanner.nextLine()
        print "Estado: "; String estado = scanner.nextLine()
        print "CEP: "; String cep = scanner.nextLine()
        print "Descricao: "; String descricao = scanner.nextLine()
        print "Senha: "; String senha = scanner.nextLine()
        List<Competencia> comps = selecionarCompetencias()
        Empresa emp = new Empresa(nome:nome, email:email, cnpj:cnpj, pais:pais, estado:estado, cep:cep, descricao:descricao, competencias:comps, senha:senha)
        try {
            empresaService.adicionar(emp)
            println "Empresa cadastrada (ID: ${emp.id})"
        } catch (Exception e) {
            if (e.message?.contains('unique') || e.message?.contains('duplicate') || e.message?.contains('violates')) {
                println "\nErro: Email ou CNPJ ja cadastrado!"
            } else {
                println "\nErro ao cadastrar: ${e.message}"
            }
        }
    }

    static void atualizarEmpresa() {
        print "ID da empresa a atualizar: "; Integer id = lerOpcao()
        def e = empresaService.buscarPorId(id)
        if (!e) { println "Empresa nao encontrada"; return }
        print "Nome [${e.nome}]: "; String nome = scanner.nextLine(); if (nome) e.nome = nome
        print "Email [${e.email}]: "; String email = scanner.nextLine(); if (email) e.email = email
        print "CNPJ [${e.cnpj}]: "; String cnpj = scanner.nextLine(); if (cnpj) e.cnpj = cnpj
        print "Pais [${e.pais}]: "; String pais = scanner.nextLine(); if (pais) e.pais = pais
        print "Estado [${e.estado}]: "; String estado = scanner.nextLine(); if (estado) e.estado = estado
        print "CEP [${e.cep}]: "; String cep = scanner.nextLine(); if (cep) e.cep = cep
        print "Descricao [${e.descricao}]: "; String desc = scanner.nextLine(); if (desc) e.descricao = desc
        println "Competencias atuais: ${e.competencias.join(', ')}"
        print "Deseja atualizar competencias? (s/N): "; String up = scanner.nextLine()
        if (up?.toLowerCase() == 's') { e.competencias = selecionarCompetencias() }
        try {
            empresaService.atualizar(e)
            println "Empresa atualizada"
        } catch (Exception ex) {
            if (ex.message?.contains('unique') || ex.message?.contains('duplicate') || ex.message?.contains('violates')) {
                println "\nErro: Email ou CNPJ ja cadastrado por outra empresa!"
            } else {
                println "\nErro ao atualizar: ${ex.message}"
            }
        }
    }

    static void deletarEmpresa() {
        print "ID da empresa a deletar: "; Integer id = lerOpcao()
        if (empresaService.remover(id)) println "Empresa removida" else println "Nao foi possivel remover"
    }

    // ---------- VAGA ----------
    static void menuVaga() {
        println "\n--- VAGAS ---"
        println "1. Listar"
        println "2. Cadastrar"
        println "3. Atualizar"
        println "4. Deletar"
        println "0. Voltar"
        print "Opcao: "
        switch (lerOpcao()) {
            case 1: listarVagas(); break
            case 2: cadastrarVaga(); break
            case 3: atualizarVaga(); break
            case 4: deletarVaga(); break
            default: break
        }
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

        print "Nome da vaga: "; String nome = scanner.nextLine()
        print "Descricao: "; String descricao = scanner.nextLine()
        print "Local (estado): "; String localVaga = scanner.nextLine()
        List<Competencia> competencias = selecionarCompetencias()

        Vaga vaga = new Vaga(nome: nome, descricao: descricao, localVaga: localVaga, empresa: empresa, competencias: competencias)
        try {
            vagaService.adicionar(vaga)
            println "\nVaga cadastrada com sucesso!"
        } catch (Exception e) {
            println "\nErro ao cadastrar vaga: ${e.message}"
        }
    }

    static void atualizarVaga() {
        print "ID da vaga a atualizar: "; Integer id = lerOpcao()
        def v = vagaService.buscarPorId(id)
        if (!v) { println "Vaga nao encontrada"; return }
        print "Nome [${v.nome}]: "; String nome = scanner.nextLine(); if (nome) v.nome = nome
        print "Descricao [${v.descricao}]: "; String descricao = scanner.nextLine(); if (descricao) v.descricao = descricao
        print "Local (estado) [${v.localVaga}]: "; String localVaga = scanner.nextLine(); if (localVaga) v.localVaga = localVaga
        println "Competencias atuais: ${v.competencias.join(', ')}"
        print "Deseja atualizar competencias? (s/N): "; String up = scanner.nextLine()
        if (up?.toLowerCase() == 's') { v.competencias = selecionarCompetencias() }
        try {
            vagaService.atualizar(v)
            println "Vaga atualizada"
        } catch (Exception e) {
            println "\nErro ao atualizar vaga: ${e.message}"
        }
    }

    static void deletarVaga() {
        print "ID da vaga a deletar: "; Integer id = lerOpcao()
        if (vagaService.remover(id)) println "Vaga removida" else println "Nao foi possivel remover"
    }

    // ---------- COMPETENCIA ----------
    static void menuCompetencia() {
        println "\n--- COMPETENCIAS ---"
        println "1. Listar"
        println "2. Cadastrar"
        println "3. Deletar"
        println "0. Voltar"
        print "Opcao: "
        switch (lerOpcao()) {
            case 1: listarCompetencias(); break
            case 2: cadastrarCompetencia(); break
            case 3: deletarCompetencia(); break
            default: break
        }
    }

    static void listarCompetencias() {
        println "\n=== COMPETENCIAS (cadastradas no BD) ==="
        def lista = competenciaService.listarTodos()
        if (lista.isEmpty()) {
            println "Nenhuma competencia cadastrada no banco."
            return
        }
        lista.eachWithIndex { c, i ->
            println "${i+1}. ${c.name()} - ${c.descricao}"
        }
    }

    static void cadastrarCompetencia() {
        println "\n=== CADASTRAR COMPETENCIA ==="
        println "Selecione uma Competencia pre-definida para adicionar ao BD:"
        Competencia.values().eachWithIndex { comp, idx -> println "${idx+1}. ${comp}" }
        print "Opcao: "
        Integer opc = lerOpcao()
        if (opc <= 0 || opc > Competencia.values().length) { println "Opcao invalida"; return }
        Competencia selected = Competencia.values()[opc-1]
        competenciaService.adicionar(selected)
        println "Competencia ${selected} registrada no BD"
    }

    static void deletarCompetencia() {
        println "\n=== REMOVER COMPETENCIA ==="
        println "Escolha a competencia a remover do BD:"
        def list = competenciaService.listarTodos()
        if (list.isEmpty()) { println "Nenhuma competencia para remover"; return }
        list.eachWithIndex { comp, idx -> println "${idx+1}. ${comp}" }
        print "Opcao: "
        Integer opc = lerOpcao()
        if (opc <= 0 || opc > list.size()) { println "Opcao invalida"; return }
        Competencia sel = list[opc-1]
        if (competenciaService.remover(sel.name())) println "Competencia ${sel} removida do BD" else println "Nao foi possivel remover"
    }

    // ---------- CURTIDA ----------
    static void menuCurtida() {
        println "\n--- CURTIDAS / MATCHS ---"
        println "1. Listar Curtidas"
        println "2. Curtir Vaga (Candidato)"
        println "3. Curtir Candidato (Empresa)"
        println "0. Voltar"
        print "Opcao: "
        switch (lerOpcao()) {
            case 1: listarCurtidas(); break
            case 2: curtirVaga(); break
            case 3: curtirCandidato(); break
            default: break
        }
    }

    static void listarCurtidas() {
        println "\n=== CURTIDAS ==="
        curtidaService.listarTodos().each { curtida ->
            println "\n--- Curtida ${curtida.id} ---"
            println curtida
        }
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

        try {
            Curtida curtida = curtidaService.candidatoCurteVaga(candidato, vaga)
            println "\nCurtida registrada!"
            if (curtida.origemCurtida == 'MATCH') {
                println "*** MATCH! ${candidato.nome} e ${vaga.empresa?.nome} deram match! ***"
            }
        } catch (Exception e) {
            if (e.message?.contains('unique') || e.message?.contains('duplicate') || e.message?.contains('violates')) {
                println "\nEsta curtida ja foi registrada anteriormente!"
            } else {
                println "\nErro ao registrar curtida: ${e.message}"
            }
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

        try {
            Curtida curtida = curtidaService.empresaCurteCandidato(empresa, candidato, vaga)
            println "\nCurtida registrada!"
            if (curtida.origemCurtida == 'MATCH') {
                println "*** MATCH! ${candidato.nome} e ${empresa.nome} deram match! ***"
            }
        } catch (Exception e) {
            if (e.message?.contains('unique') || e.message?.contains('duplicate') || e.message?.contains('violates')) {
                println "\nEsta curtida ja foi registrada anteriormente!"
            } else {
                println "\nErro ao registrar curtida: ${e.message}"
            }
        }
    }

    static int lerOpcao() {
        try {
            String line = scanner.nextLine()
            if (line ==~ /\d+/) {
                return line.toInteger()
            }
            return -1
        } catch (Exception ignored) {
            return 0
        }
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
            def token = num.trim()
            if (token ==~ /\d+/) {
                int index = token.toInteger() - 1
                if (index >= 0 && index < Competencia.values().length) {
                    selecionadas.add(Competencia.values()[index])
                }
            }
        }

        return selecionadas
    }
}

