package com.diegoassuncao.barmanager.exception;

import com.diegoassuncao.barmanager.web.dto.ErroResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> tratarResourceNotFound(ResourceNotFoundException excecao) {
        log.warn("Erro de negócio: {}", excecao.getMessage());
        return montarResposta(HttpStatus.NOT_FOUND, excecao.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> tratarValidacao(MethodArgumentNotValidException excecao) {
        String mensagens = excecao.getBindingResult().getFieldErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validação falhou: {}", mensagens);
        return montarResposta(HttpStatus.BAD_REQUEST, mensagens);
    }

    @ExceptionHandler(NomeDuplicadoException.class)
    public ResponseEntity<ErroResponseDTO> tratarNomeDuplicado(NomeDuplicadoException excecao) {
        log.warn("Erro de negócio: {}", excecao.getMessage());
        return montarResposta(HttpStatus.CONFLICT, excecao.getMessage());
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErroResponseDTO> tratarRegistroDuplicado(RegistroDuplicadoException excecao) {
        log.warn("Erro de negócio: {}", excecao.getMessage());
        return montarResposta(HttpStatus.CONFLICT, excecao.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class, DisabledException.class})
    public ResponseEntity<ErroResponseDTO> credencialInvalida(AuthenticationException excecao) {
        log.warn("Falha de login: credenciais inválidas");
        return montarResposta(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<ErroResponseDTO> tratarOperacaoNaoPermitida(OperacaoNaoPermitidaException excecao) {
        log.warn("Operação não permitida: {}", excecao.getMessage());
        return montarResposta(HttpStatus.CONFLICT, excecao.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> tratarCorpoInvalido(HttpMessageNotReadableException excecao) {
        log.warn("Corpo da requisição inválido");
        return montarResposta(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResponseDTO> tratarTipoParametroInvalido(MethodArgumentTypeMismatchException excecao) {
        log.warn("Parâmetro inválido: {}", excecao.getName());
        return montarResposta(HttpStatus.BAD_REQUEST,
                "Valor inválido para o parâmetro '" + excecao.getName() + "'");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> tratarErroInesperado(Exception excecao) {
        log.error("Erro inesperado", excecao);
        return montarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno. Tente novamente mais tarde");
    }

    private ResponseEntity<ErroResponseDTO> montarResposta(HttpStatus status, String mensagem) {
        ErroResponseDTO erro = new ErroResponseDTO(status.value(), mensagem, LocalDateTime.now());
        return ResponseEntity.status(status).body(erro);
    }
}