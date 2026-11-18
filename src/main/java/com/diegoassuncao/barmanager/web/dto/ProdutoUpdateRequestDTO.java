package com.diegoassuncao.barmanager.web.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @Pattern(regexp = ".*\\S.*", message = "Nome não pode ser vazio")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @Pattern(regexp = ".*\\S.*", message = "Categoria não pode ser vazia")
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    @PositiveOrZero(message = "Preço de custo não pode ser negativo")
    private BigDecimal precoCusto;

    @Positive(message = "Preço de venda deve ser maior que zero")
    private BigDecimal precoVenda;

    @PositiveOrZero(message = "Quantidade não pode ser negativa")
    private Integer quantidade;

}
