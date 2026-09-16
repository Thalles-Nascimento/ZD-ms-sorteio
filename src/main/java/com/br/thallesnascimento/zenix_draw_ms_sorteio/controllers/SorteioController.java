package com.br.thallesnascimento.zenix_draw_ms_sorteio.controllers;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTOResponse;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.services.SorteioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/sorteio")
@Tag(name = "Sorteio", description = "Endpoints do serviço de Sorteio")
public class SorteioController {

    @Autowired
    private SorteioService sorteioService;

    @PostMapping
    @Operation(summary = "Endpoint para envio da lista", description = "Endpoint para receber a lista de nomes para sortear os times")
    public ResponseEntity<ListaDTOResponse> sortear(@RequestBody ListaDTO listaDTO){
        log.info("[CONTROLLER]: SorteioController.sortear(linha: 28) => POST {/api/v1/sorteio}") ;
        log.info("Iniciando o sorteio...");

        ResponseEntity<ListaDTOResponse> response = ResponseEntity.status(HttpStatus.CREATED).body(sorteioService.sortearTimes(listaDTO));

        log.info("[CONTROLLER] Response: [Status {}]", response.getStatusCode());

        return response;
    }
}
