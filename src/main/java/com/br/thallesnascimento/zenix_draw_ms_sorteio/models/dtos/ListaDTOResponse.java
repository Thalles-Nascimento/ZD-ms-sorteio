package com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogadores;

import java.util.List;
import java.util.Map;

public record ListaDTOResponse(Map<String, List<Jogadores>> times, List<Jogadores> reservas, int jogadoresPorTime) {
}
