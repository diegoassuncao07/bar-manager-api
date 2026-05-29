package com.diegoassuncao.barmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "produto")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @Column(name = "categoria", length = 100, nullable = false)
    private String categoria;
    @Column(name = "preco_custo", precision = 10, scale = 2)
    private BigDecimal precoCusto;
    @Column(name = "preco_venda", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoVenda;
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

}
