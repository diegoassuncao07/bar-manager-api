package com.diegoassuncao.barmanager.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginRequestDTO {
    @NotBlank(message = "Nome de usuário obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "Senha obrigatória")
    private String senha;
}
