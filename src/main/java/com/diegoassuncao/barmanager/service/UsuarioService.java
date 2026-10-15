package com.diegoassuncao.barmanager.service;

import com.diegoassuncao.barmanager.exception.RegistroDuplicadoException;
import com.diegoassuncao.barmanager.infrastructure.entity.Usuario;
import com.diegoassuncao.barmanager.infrastructure.repository.UsuarioRepository;
import com.diegoassuncao.barmanager.web.dto.CadastroUsuarioDTO;
import com.diegoassuncao.barmanager.web.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public String gerarUsername(String nomeCompleto){
        String[] partes = nomeCompleto.split(" ");
        String primeiroNome = partes[0];
        String ultimoNome = partes[partes.length - 1];
        String nomeUsuario = primeiroNome + "-" + ultimoNome;
        nomeUsuario = nomeUsuario.toLowerCase();

        nomeUsuario = Normalizer.normalize(nomeUsuario, Normalizer.Form.NFD);
        nomeUsuario = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(nomeUsuario)
                .replaceAll("");

        int dia = LocalDate.now().getDayOfMonth();
        String diaFormatado = String.format("%02d", dia);

        return nomeUsuario + diaFormatado;
    }

    public void validarDadosUnicos(String email, String nomeUsuario, String documento){
        if(usuarioRepository.existsByNomeUsuarioIgnoreCase(nomeUsuario)){
            throw new RegistroDuplicadoException("Este nome de usuário já existe");
        }
        if(usuarioRepository.existsByEmailIgnoreCase(email)){
            throw new RegistroDuplicadoException("Este email já existe");
        }
        if(usuarioRepository.existsByDocumento(documento)){
            throw new RegistroDuplicadoException("Documento já cadastrado");
        }
    }

    public UsuarioResponseDTO criarUsuario(CadastroUsuarioDTO cadastroUsuarioDTO){
        String usernameUsuario = gerarUsername(cadastroUsuarioDTO.getNomeCompleto());
        validarDadosUnicos(cadastroUsuarioDTO.getEmail(), usernameUsuario, cadastroUsuarioDTO.getDocumento());

        Usuario usuario = Usuario.builder()
                .nomeCompleto(cadastroUsuarioDTO.getNomeCompleto())
                .documento(cadastroUsuarioDTO.getDocumento())
                .nomeUsuario(usernameUsuario)
                .email(cadastroUsuarioDTO.getEmail())
                .senha(passwordEncoder.encode(cadastroUsuarioDTO.getSenha()))
                .perfil(cadastroUsuarioDTO.getPerfil())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getNomeUsuario(),
                usuarioSalvo.getNomeCompleto(),
                usuarioSalvo.getPerfil()
        );

    }

}
