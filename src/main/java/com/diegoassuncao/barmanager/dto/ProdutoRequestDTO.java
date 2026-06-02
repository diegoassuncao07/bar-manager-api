package com.diegoassuncao.barmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProdutoRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    private BigDecimal precoCusto;

    @NotNull(message = "Preço de venda é obrigatório")
    @Positive(message = "Preço de venda deve ser maior que zero")
    private BigDecimal precoVenda;

    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade;

}
