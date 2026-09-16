package com.br.thallesnascimento.zenix_draw_ms_sorteio.services;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTOResponse;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogadores;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class SorteioService {

    private final Random random = new Random();

    public ListaDTOResponse sortearTimes(ListaDTO listaDTO){
        int numeroTimes = listaDTO.numeroTimes();
        Map<String, List<Jogadores>> times = new HashMap<>();

        int qtdJogadoresPorTime = listaDTO.jogadores().size() / numeroTimes;
        for (int i = 0; i < numeroTimes; i++) {
            List<Jogadores> jogadoresPorTime = new ArrayList<>(qtdJogadoresPorTime);

            for (int j = 0; j < qtdJogadoresPorTime; j++) {

                int indice = this.random.nextInt(listaDTO.jogadores().size());
                Jogadores jogadorSelecionado = listaDTO.jogadores().get(indice);

                jogadoresPorTime.add(jogadorSelecionado);
                listaDTO.jogadores().remove(jogadorSelecionado);

            }
            jogadoresPorTime.sort(Comparator.comparing(Jogadores::getPosicao));

            times.put("time" + (i+1), jogadoresPorTime);

        }
        return new ListaDTOResponse(times, listaDTO.jogadores(), qtdJogadoresPorTime);
    }

}
