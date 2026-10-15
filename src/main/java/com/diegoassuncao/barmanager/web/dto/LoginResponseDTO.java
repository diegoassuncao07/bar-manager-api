package com.diegoassuncao.barmanager.web.dto;

import com.diegoassuncao.barmanager.infrastructure.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String nomeCompleto;

    private Role perfil;

    private String token;
}
