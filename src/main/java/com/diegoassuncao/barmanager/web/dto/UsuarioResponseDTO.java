package com.diegoassuncao.barmanager.web.dto;

import com.diegoassuncao.barmanager.infrastructure.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioResponseDTO {
    private Long id;

    private String nomeUsuario;

    private String nomeCompleto;

    private Role perfil;
}
