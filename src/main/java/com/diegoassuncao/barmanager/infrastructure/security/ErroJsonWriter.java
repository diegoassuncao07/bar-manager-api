package com.diegoassuncao.barmanager.infrastructure.security;

import com.diegoassuncao.barmanager.web.dto.ErroResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor

public class ErroJsonWriter {

    private final ObjectMapper objectMapper;

    public void escrever(HttpServletResponse response, HttpStatus status, String mensagem)
            throws IOException {
        ErroResponseDTO erro = new ErroResponseDTO(status.value(), mensagem, LocalDateTime.now());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(), erro);
    }
}
