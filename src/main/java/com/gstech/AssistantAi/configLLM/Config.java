package com.gstech.AssistantAi.configLLM;

import com.gstech.AssistantAi.configLLM.interfaces.AiAssistantService;
import com.gstech.AssistantAi.service.BudgetToolService;
import com.gstech.AssistantAi.tools.MenuToolsService;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final String openAiApiKey;
    private final String openAiModelName;

    public Config(@Value("${OPENAI_API_KEY:}") String openAiApiKey,
                  @Value("${OPENAI_MODEL:}") String openAiModelName) {
        this.openAiApiKey = openAiApiKey;
        this.openAiModelName = openAiModelName;
    }

    @Bean
    public ChatModel chatModel() {
        if (openAiApiKey.isBlank()) {
            throw new IllegalStateException("Defina OPENAI_API_KEY no arquivo .env para usar a OpenAI.");
        }
        if (openAiModelName.isBlank()) {
            throw new IllegalStateException("Defina OPENAI_MODEL no arquivo .env para usar a OpenAI.");
        }

        return OpenAiChatModel.builder()
                .apiKey(openAiApiKey)
                .modelName(openAiModelName)
                .reasoningEffort("none")
                .build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.withMaxMessages(30);
    }

    @Bean
    public AiAssistantService assistantService(ChatMemoryProvider memory, ChatModel model,
                                               MenuToolsService menuTools, BudgetToolService budgetTools) {

        return AiServices.builder(AiAssistantService.class)
                .chatModel(model)
                .chatMemoryProvider(memory)
                .tools(menuTools, budgetTools)
                .build();
    }
}
