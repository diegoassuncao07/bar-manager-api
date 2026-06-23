package com.diegoassuncao.barmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ErroResponseDTO {

    private Integer status;
    private String mensagem;
    private LocalDateTime timestamp;

}
