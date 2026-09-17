package com.br.thallesnascimento.zenix_draw_ms_sorteio.services;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTOResponse;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogadores;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.enums.Posicao;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SorteioServiceTest {
    private SorteioService sorteioService;
    private ListaDTO jogadores;

    @BeforeEach
    void setUp() {
        sorteioService = new SorteioService();
        Jogadores jogador1 = new Jogadores();
        Jogadores jogador2 = new Jogadores();
        Jogadores jogador3 = new Jogadores();
        Jogadores jogador4 = new Jogadores();
        jogador1.setNome("Thalles");
        jogador1.setForca(5.2f);
        jogador1.setPosicao(Posicao.ATACANTE);
        jogador2.setNome("Gustavo");
        jogador2.setForca(5.0f);
        jogador2.setPosicao(Posicao.ZAGUEIRO);
        jogador3.setNome("Jorlan");
        jogador3.setForca(4.8f);
        jogador3.setPosicao(Posicao.GOLEIRO);
        jogador4.setNome("Mattheus");
        jogador4.setForca(5.3f);
        jogador4.setPosicao(Posicao.LATERAL);
        List<Jogadores> listJogadores = new ArrayList<>();
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
        assertTrue(ordenada(result.times().get("time1")), "Lista deve estar ordenada");
        assertTrue(ordenada(result.times().get("time2")), "Lista deve estar ordenada");

    }

    private boolean ordenada(@NonNull List<Jogadores> list){
        Jogadores jogador1 = list.getFirst();
        Jogadores jogador2 = list.getLast();
        System.out.println("Jogador 1 = " + jogador1);
        System.out.println("Jogador 2 = " + jogador2);

        return jogador2.getPosicao().ordinal() > jogador1.getPosicao().ordinal();
    }
}