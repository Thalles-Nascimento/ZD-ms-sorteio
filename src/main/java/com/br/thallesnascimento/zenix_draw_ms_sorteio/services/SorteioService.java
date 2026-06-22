package com.br.thallesnascimento.zenix_draw_ms_sorteio.services;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogadores;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTOResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SorteioService {

    public ListaDTOResponse sortearTimes(ListaDTO listaDTO){
        int numeroTimes = listaDTO.numeroTimes();
        Map<String, List<Jogadores>> times = new HashMap<>();

        int qtdJogadoresPorTime = listaDTO.jogadores().size() / numeroTimes;
        for (int i = 0; i < numeroTimes; i++) {
            List<Jogadores> jogadoresPorTime = new ArrayList<>(qtdJogadoresPorTime);

            for (int j = 0; j < qtdJogadoresPorTime; j++) {
                int numeroJogadores = listaDTO.jogadores().size();
                int indice = (int) (Math.random() * numeroJogadores);
                Jogadores jogadorSelecionado = listaDTO.jogadores().get(indice);

                jogadoresPorTime.add(jogadorSelecionado);
                listaDTO.jogadores().remove(indice);

            }
            jogadoresPorTime.sort(Comparator.comparing(Jogadores::getPosicao));

            times.put("time" + (i+1), jogadoresPorTime);

        }
        return new ListaDTOResponse(times, listaDTO.jogadores(), qtdJogadoresPorTime);
    }

}
