package com.diegoassuncao.barmanager.web.dto;

import com.diegoassuncao.barmanager.infrastructure.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres")
    private String senha;

    @NotNull(message = "Selecione o perfil Campo Obrigatório")
    private Role perfil;

}
