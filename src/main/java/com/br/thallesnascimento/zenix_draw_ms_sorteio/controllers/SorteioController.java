package com.br.thallesnascimento.zenix_draw_ms_sorteio.controllers;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTOResponse;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.services.SorteioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/sorteio")
@Tag(name = "Sorteio", description = "Endpoints do serviço de Sorteio")
public class SorteioController {

    @Autowired
    private SorteioService sorteioService;

    @PostMapping
    @Operation(summary = "Endpoint para envio da lista", description = "Endpoint para receber a lista de nomes para sortear os times")
    public ResponseEntity<ListaDTOResponse> sortear(@RequestBody ListaDTO listaDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(sorteioService.sortearTimes(listaDTO));
    }
}
