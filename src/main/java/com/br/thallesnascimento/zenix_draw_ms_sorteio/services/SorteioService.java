package com.br.thallesnascimento.zenix_draw_ms_sorteio.services;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.dtos.ListaDTO;
import com.br.thallesnascimento.zenix_draw_ms_sorteio.dtos.ListaDTOResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SorteioService {

    public ListaDTOResponse sortearTimes(ListaDTO listaDTO){
        List<String> lista = listaDTO.nomes();

        if (lista.size() % 2f == 0){
            List<String> time1 = new ArrayList<>();
            List<String> time2 = new ArrayList<>();

            for (int i = 0; i <= lista.size() - 1 ; i+=2) {
                time1.add(lista.get(i));
                time2.add(lista.get(i + 1));
            }

            return new ListaDTOResponse(time1, time2, null);

        }
        else if (lista.size() % 3f == 0){
            List<String> time1 = new ArrayList<>();
            List<String> time2 = new ArrayList<>();
            List<String> time3 = new ArrayList<>();

            for (int i = 0; i < lista.size() - 1 ; i+=3) {
                time1.add(lista.get(i));
                time2.add(lista.get(i + 1));
                time3.add(lista.get(i + 2));
            }

            return new ListaDTOResponse(time1, time2, time3);
        }
        return null;
    }

}
