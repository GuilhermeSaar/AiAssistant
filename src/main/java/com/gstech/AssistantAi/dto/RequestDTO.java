package com.gstech.AssistantAi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestDTO(
        @JsonAlias("UserMessage")
        @NotBlank(message = "Informe uma mensagem.")
        @Size(max = 4000, message = "A mensagem deve ter no máximo 4000 caracteres.")
        String userMessage,
        @Size(max = 64, message = "O identificador da conversa deve ter no máximo 64 caracteres.")
        String id
) {
}
