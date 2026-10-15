package com.diegoassuncao.barmanager.web.controller;

import com.diegoassuncao.barmanager.service.UsuarioService;
import com.diegoassuncao.barmanager.web.dto.CadastroUsuarioDTO;
import com.diegoassuncao.barmanager.web.dto.UsuarioResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
