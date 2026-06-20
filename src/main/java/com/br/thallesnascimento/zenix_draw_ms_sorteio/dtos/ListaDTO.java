package com.br.thallesnascimento.zenix_draw_ms_sorteio.dtos;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.Jogadores;

import java.util.List;

public record ListaDTO(List<Jogadores> jogadores, int numeroTimes) {
}
