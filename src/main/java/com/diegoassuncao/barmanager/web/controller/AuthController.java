package com.diegoassuncao.barmanager.web.controller;

import com.diegoassuncao.barmanager.service.AuthService;
import com.diegoassuncao.barmanager.web.dto.LoginRequestDTO;
import com.diegoassuncao.barmanager.web.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        LoginResponseDTO userLogado = authService.login(loginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userLogado);
    }
}
