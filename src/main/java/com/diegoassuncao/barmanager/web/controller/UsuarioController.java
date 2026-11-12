package com.diegoassuncao.barmanager.web.controller;

import com.diegoassuncao.barmanager.infrastructure.entity.Usuario;
import com.diegoassuncao.barmanager.service.UsuarioService;
import com.diegoassuncao.barmanager.web.dto.CadastroUsuarioDTO;
import com.diegoassuncao.barmanager.web.dto.UsuarioListagemDTO;
import com.diegoassuncao.barmanager.web.dto.UsuarioResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor

public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid CadastroUsuarioDTO cadastroUsuarioDTO){
        UsuarioResponseDTO usuarioCriado = usuarioService.criarUsuario(cadastroUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioListagemDTO>> listarUsuarios(@RequestParam(defaultValue = "true")
                                                                       boolean ativo){
        return ResponseEntity.ok(usuarioService.listarUsuarios(ativo));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> desativarUsuario(@PathVariable Long idUsuario, @AuthenticationPrincipal Usuario usuarioLogado){
        usuarioService.desativarUsuario(idUsuario, usuarioLogado.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{idUsuario}/reativar")
    public ResponseEntity<Void> reativarUsuario(@PathVariable Long idUsuario){
        usuarioService.reativarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
