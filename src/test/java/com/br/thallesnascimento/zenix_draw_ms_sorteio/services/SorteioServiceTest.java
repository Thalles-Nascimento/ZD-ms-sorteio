package com.br.thallesnascimento.zenix_draw_ms_sorteio.services;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTOResponse;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogador;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.enums.Posicao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class SorteioServiceTest {
    private SorteioService sorteioService;
    private ListaDTO jogadores;

    @BeforeEach
    void setUp() {
        sorteioService = new SorteioService();

        // 1. Jogador
        Jogador jogador1 = Jogador.builder()
                .nome("Thalles")
                .posicao(Posicao.ATACANTE)
                .build();

        // 2. Jogador
        Jogador jogador2 = Jogador.builder()
                .nome("Gustavo")
                .posicao(Posicao.ZAGUEIRO)
                .build();

        // 3. Jogador
        Jogador jogador3 = Jogador.builder()
                .nome("Jorlan")
                .posicao(Posicao.GOLEIRO)
                .build();

        // 4. Jogador
        Jogador jogador4 = Jogador.builder()
                .nome("Mattheus")
                .posicao(Posicao.LATERAL)
                .build();

        List<Jogador> listJogadores = new ArrayList<>();
        listJogadores.add(jogador1);
        listJogadores.add(jogador2);
        listJogadores.add(jogador3);
        listJogadores.add(jogador4);
        jogadores = new ListaDTO(listJogadores, 2);
    }

    @Test
    void deveSortearDoisTimesSemReservas() {
        ListaDTOResponse result = sorteioService.sortearTimes(jogadores);
        assertEquals(2, result.times().size());
        assertEquals(0, result.reservas().size());

    }

    @Test
    void listaDeJogadoresDeveEstarOrdenadaPorPosicao() {
        ListaDTOResponse result = sorteioService.sortearTimes(jogadores);
        assertThat(result.times().get("time1")).isSortedAccordingTo(Comparator.comparing(Jogador::getPosicao));
        assertThat(result.times().get("time2")).isSortedAccordingTo(Comparator.comparing(Jogador::getPosicao));

    }

    @Test
    void naoDeveConterNulos() {
        ListaDTOResponse result = sorteioService.sortearTimes(jogadores);
        assertNotNull(result);
        assertThat(result.times().get("time1"))
                .isNotNull()
                .isNotEmpty();
        assertThat(result.times().get("time2"))
                .isNotNull()
                .isNotEmpty();
    }
}