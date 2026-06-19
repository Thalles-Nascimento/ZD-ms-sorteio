package com.br.thallesnascimento.zenix_draw_ms_sorteio.services;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.dtos.ListaDTOResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SorteioService {

    public ListaDTOResponse sortearTimes(ListaDTO listaDTO){
        int numeroTimes = listaDTO.numeroTimes();
        Map<String, List<String>> times = new HashMap<>();

        int qtdJogadoresPorTime = listaDTO.jogadores().size() / numeroTimes;
        for (int i = 0; i < numeroTimes; i++) {
            List<String> jogadoresPorTime = new ArrayList<>(qtdJogadoresPorTime);

            for (int j = 0; j < qtdJogadoresPorTime; j++) {
                jogadoresPorTime.add(listaDTO.jogadores().getFirst());
                listaDTO.jogadores().removeFirst();
            }

            times.put("time" + (i+1), jogadoresPorTime);

        }
        return new ListaDTOResponse(times);
    }

}
