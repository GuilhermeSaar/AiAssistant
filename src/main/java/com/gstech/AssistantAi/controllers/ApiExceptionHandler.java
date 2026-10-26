package com.gstech.AssistantAi.controllers;

import com.gstech.AssistantAi.dto.ApiErrorDTO;
import dev.langchain4j.exception.AuthenticationException;
import dev.langchain4j.exception.InternalServerException;
import dev.langchain4j.exception.ModelNotFoundException;
import dev.langchain4j.exception.RateLimitException;
import dev.langchain4j.exception.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Confira os dados enviados.");
        return error(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorDTO> handleInvalidJson() {
        return error(HttpStatus.BAD_REQUEST, "Envie um JSON válido com o campo userMessage.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleInvalidInput(IllegalArgumentException exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<ApiErrorDTO> handleRateLimit() {
        LOGGER.warn("O provedor de IA recusou a chamada por limite de uso.");
        return error(HttpStatus.TOO_MANY_REQUESTS,
                "O limite de uso do assistente foi atingido. Aguarde e tente novamente mais tarde.");
    }

    @ExceptionHandler({InternalServerException.class, ModelNotFoundException.class, AuthenticationException.class})
    public ResponseEntity<ApiErrorDTO> handleProviderUnavailable(Exception exception) {
        LOGGER.warn("Provedor de IA indisponível: {}", exception.getClass().getSimpleName());
        return error(HttpStatus.SERVICE_UNAVAILABLE,
                "O serviço de IA está temporariamente indisponível. Tente novamente em instantes.");
    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<ApiErrorDTO> handleProviderTimeout() {
        LOGGER.warn("O provedor de IA excedeu o tempo de resposta.");
        return error(HttpStatus.GATEWAY_TIMEOUT,
                "O assistente demorou para responder. Tente novamente em instantes.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleUnexpectedError(Exception exception) {
        LOGGER.error("Falha inesperada ao atender a solicitação.", exception);
        return error(HttpStatus.INTERNAL_SERVER_ERROR,
                "Não foi possível concluir sua solicitação. Tente novamente.");
    }

    private ResponseEntity<ApiErrorDTO> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ApiErrorDTO(message));
    }
}
