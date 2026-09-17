package com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogador;

import java.util.List;

public record ListaDTO(List<Jogador> jogadores, int numeroTimes) {
}
