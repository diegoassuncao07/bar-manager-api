package com.diegoassuncao.barmanager.infrastructure.security;

import com.diegoassuncao.barmanager.infrastructure.entity.Usuario;
import com.diegoassuncao.barmanager.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNomeUsuarioIgnoreCase(nomeUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + nomeUsuario));

        return usuario;
    }
}
