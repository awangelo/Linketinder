package com.awangelo

import com.awangelo.controller.CandidatoController
import com.awangelo.controller.EmpresaController
import com.awangelo.controller.VagaController
import com.awangelo.dao.CandidatoDAO
import com.awangelo.dao.EmpresaDAO
import com.awangelo.dao.VagaDAO
import com.awangelo.router.Router
import com.awangelo.service.CandidatoService
import com.awangelo.service.EmpresaService
import com.awangelo.service.VagaService
import io.undertow.Undertow

class Main {

    static void main(String[] args) {
        CandidatoService candidatoService = new CandidatoService(new CandidatoDAO())
        EmpresaService empresaService = new EmpresaService(new EmpresaDAO())
        VagaService vagaService = new VagaService(new VagaDAO())

        CandidatoController candidatoController = new CandidatoController(candidatoService)
        EmpresaController empresaController = new EmpresaController(empresaService)
        VagaController vagaController = new VagaController(vagaService)

        Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(Router.getRouter(candidatoController, empresaController, vagaController))
                .build()
                .start()
    }
}