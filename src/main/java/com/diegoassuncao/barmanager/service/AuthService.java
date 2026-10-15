package com.diegoassuncao.barmanager.service;
import com.diegoassuncao.barmanager.infrastructure.entity.Usuario;
import com.diegoassuncao.barmanager.infrastructure.security.JwtService;
import com.diegoassuncao.barmanager.web.dto.LoginRequestDTO;
import com.diegoassuncao.barmanager.web.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Authentication autenticacao = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getNomeUsuario(),
                        loginRequestDTO.getSenha()
                )
        );

        Usuario usuario = (Usuario) autenticacao.getPrincipal();

        String token = jwtService.gerarToken(usuario);

        return new LoginResponseDTO(
                usuario.getNomeCompleto(),
                usuario.getPerfil(),
                token
        );
    }


}
