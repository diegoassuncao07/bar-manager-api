package com.diegoassuncao.barmanager.web.dto;

import com.diegoassuncao.barmanager.infrastructure.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroUsuarioDTO {
    @NotBlank(message = "Nome Completo obrigatório")
    private String nomeCompleto;

    @NotBlank(message = "Documento CPF obrigatório")
    private String documento;

    @NotBlank(message = "Email obrigatório")
    private String email;

    @NotBlank(message = "Senha obrigatório")
    private String senha;

    @NotNull(message = "Selecione o perfil Campo Obrigatório")
    private Role perfil;

}
