package com.br.thallesnascimento.zenix_draw_ms_sorteio.services;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.dtos.ListaDTOResponse;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.models.entities.Jogador;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class SorteioService {

    private final Random random = new Random();

    public ListaDTOResponse sortearTimes(ListaDTO listaDTO){
        long inicio = System.currentTimeMillis();
        log.info("[SERVICE]: SorteioService.sortearTimes(linha 18)");
        int numeroTimes = listaDTO.numeroTimes();
        Map<String, List<Jogador>> times = new HashMap<>();

        int qtdJogadoresPorTime = listaDTO.jogadores().size() / numeroTimes;
        for (int i = 0; i < numeroTimes; i++) {
            List<Jogador> jogadoresPorTime = new ArrayList<>(qtdJogadoresPorTime);

            for (int j = 0; j < qtdJogadoresPorTime; j++) {

                int indice = this.random.nextInt(listaDTO.jogadores().size());
                Jogador jogadorSelecionado = listaDTO.jogadores().get(indice);

                jogadoresPorTime.add(jogadorSelecionado);
                listaDTO.jogadores().remove(jogadorSelecionado);

            }
            jogadoresPorTime.sort(Comparator.comparing(Jogador::getPosicao));

            times.put("time" + (i+1), jogadoresPorTime);

        }
        long fim = System.currentTimeMillis();
        ListaDTOResponse response = new ListaDTOResponse(times, listaDTO.jogadores(), qtdJogadoresPorTime);
        log.info("Times formados. Tempo de execução: {}ms", (fim - inicio));
        log.info("[SERVICE] Response: [Status = {}] => [Message = {}]", HttpStatus.CREATED, "Times criados");
        return response;
    }

}
