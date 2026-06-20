package com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogadores;

import java.util.List;

public record ListaDTO(List<Jogadores> jogadores, int numeroTimes) {
}
