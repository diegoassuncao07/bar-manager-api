package com.diegoassuncao.barmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProdutoUpdateRequestDTO {

    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private Integer quantidade;

}
