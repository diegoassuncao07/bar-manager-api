package com.diegoassuncao.barmanager.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ErroJsonWriter erroJsonWriter;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        erroJsonWriter.escrever(response, HttpStatus.FORBIDDEN, "Acesso negado");
    }
}
