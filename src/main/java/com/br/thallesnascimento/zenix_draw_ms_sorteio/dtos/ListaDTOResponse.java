package com.br.thallesnascimento.zenix_draw_ms_sorteio.dtos;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.Jogadores;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.enums.Posicao;

import java.util.List;
import java.util.Map;

public record ListaDTOResponse(Map<String, List<Jogadores>> times, List<Jogadores> reservas, int jogadoresPorTime) {
}
