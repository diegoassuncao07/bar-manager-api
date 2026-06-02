package com.diegoassuncao.barmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal precoVenda;
    private Integer quantidade;
    private Boolean ativo;

}
