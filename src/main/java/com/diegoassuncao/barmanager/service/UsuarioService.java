package com.diegoassuncao.barmanager.service;

import com.diegoassuncao.barmanager.exception.OperacaoNaoPermitidaException;
import com.diegoassuncao.barmanager.exception.RegistroDuplicadoException;
import com.diegoassuncao.barmanager.exception.ResourceNotFoundException;
import com.diegoassuncao.barmanager.infrastructure.entity.Role;
import com.diegoassuncao.barmanager.infrastructure.entity.Usuario;
import com.diegoassuncao.barmanager.infrastructure.repository.UsuarioRepository;
import com.diegoassuncao.barmanager.web.dto.CadastroUsuarioDTO;
import com.diegoassuncao.barmanager.web.dto.UsuarioListagemDTO;
import com.diegoassuncao.barmanager.web.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public String gerarUsername(String nomeCompleto) {
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

    public void validarDadosUnicos(String email, String nomeUsuario, String documento) {
        if (usuarioRepository.existsByNomeUsuarioIgnoreCase(nomeUsuario)) {
            throw new RegistroDuplicadoException("Este nome de usuário já existe");
        }
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new RegistroDuplicadoException("Este email já existe");
        }
        if (usuarioRepository.existsByDocumento(documento)) {
            throw new RegistroDuplicadoException("Documento já cadastrado");
        }
    }

    public UsuarioResponseDTO criarUsuario(CadastroUsuarioDTO cadastroUsuarioDTO) {
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

    private String mascararDocumento(String documento) {
        if (documento == null || documento.length() < 4) {
            return "*******";
        }
        return "*******" + documento.substring(documento.length() - 4);
    }

    private UsuarioListagemDTO converterParaListagemDTO(Usuario usuario) {
        return new UsuarioListagemDTO(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getNomeCompleto(),
                usuario.getEmail(),
                mascararDocumento(usuario.getDocumento()),
                usuario.getPerfil()
        );
    }

    @Transactional(readOnly = true)
    public List<UsuarioListagemDTO> listarUsuarios(boolean ativo) {
        return usuarioRepository.findAllByAtivoOrderByIdAsc(ativo).stream()
                .map(this::converterParaListagemDTO)
                .toList();
    }

    @Transactional
    public void desativarUsuario(Long idUsuario, Long idUsuarioLogado) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario.getId().equals(idUsuarioLogado)) {
            throw new OperacaoNaoPermitidaException("Você não pode desativar o próprio usuário");
        }
        if (!usuario.isAtivo()) {
            return;
        }
        if (usuario.getPerfil() == Role.ADMIN
                && usuarioRepository.countByPerfilAndAtivoTrue(Role.ADMIN) <= 1) {
            throw new OperacaoNaoPermitidaException("Não é possível desativar o último ADMIN ativo");
        }
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void reativarUsuario(Long idUsuario) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }

    private Usuario buscarUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
