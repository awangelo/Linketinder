package com.awangelo.service

import com.awangelo.model.Vaga
import com.awangelo.model.Empresa
import com.awangelo.model.Competencia

class VagaService {
    private List<Vaga> vagas = []
    private Integer proximoId = 1
    private EmpresaService empresaService

    VagaService(EmpresaService empresaService) {
        this.empresaService = empresaService
        carregarDadosIniciais()
    }

    private void carregarDadosIniciais() {
        List<Empresa> empresas = empresaService.listarTodos()

        vagas = [
            new Vaga(
                id: proximoId++,
                nome: "Desenvolvedor Java Senior",
                descricao: "Desenvolvimento de APIs REST",
                local: "SP",
                empresa: empresas[0],
                competencias: [Competencia.JAVA, Competencia.SPRING_FRAMEWORK, Competencia.SQL]
            ),
            new Vaga(
                id: proximoId++,
                nome: "Frontend Developer",
                descricao: "Desenvolvimento de interfaces web",
                local: "RJ",
                empresa: empresas[1],
                competencias: [Competencia.JAVASCRIPT, Competencia.REACT]
            ),
            new Vaga(
                id: proximoId++,
                nome: "Engenheiro de Dados",
                descricao: "Construcao de pipelines de dados",
                local: "MG",
                empresa: empresas[2],
                competencias: [Competencia.PYTHON, Competencia.SQL, Competencia.AWS]
            ),
            new Vaga(
                id: proximoId++,
                nome: "Desenvolvedor Groovy",
                descricao: "Automacao e desenvolvimento backend",
                local: "PR",
                empresa: empresas[3],
                competencias: [Competencia.GROOVY, Competencia.JAVA]
            ),
            new Vaga(
                id: proximoId++,
                nome: "DevOps Engineer",
                descricao: "Gerenciamento de infraestrutura cloud",
                local: "SC",
                empresa: empresas[4],
                competencias: [Competencia.AWS, Competencia.PYTHON]
            )
        ]
    }

    List<Vaga> listarTodos() {
        return vagas
    }

    Vaga buscarPorId(Integer id) {
        return vagas.find { it.id == id }
    }

    List<Vaga> buscarPorEmpresa(Empresa empresa) {
        return vagas.findAll { it.empresa?.id == empresa?.id }
    }

    void adicionar(Vaga vaga) {
        vaga.id = proximoId++
        vagas.add(vaga)
    }
}
