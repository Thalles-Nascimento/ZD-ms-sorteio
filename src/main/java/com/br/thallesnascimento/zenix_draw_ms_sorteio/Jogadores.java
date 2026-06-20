package com.br.thallesnascimento.zenix_draw_ms_sorteio;

import com.br.thallesnascimento.zenix_draw_ms_sorteio.enums.Posicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jogadores {
    private String nome;
    private Posicao posicao;

}
