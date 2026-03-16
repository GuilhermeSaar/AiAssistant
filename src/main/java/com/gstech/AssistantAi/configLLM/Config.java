package com.gstech.AssistantAi.configLLM;

import com.gstech.AssistantAi.configLLM.interfaces.AiAssistantService;
import com.gstech.AssistantAi.service.BudgetToolService;
import com.gstech.AssistantAi.service.MenuToolsService;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Config {


    // parametros de config do modelo LLM
    @Value("${ollama-ai.chat-model.base.url}")
    private String baseUrl;
    @Value("${ollama-ai.chat-model.model-name}")
    private String modelName;

     //modelo de LLM
    @Bean
    public ChatModel ollamaModel() {

        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(120))
                .temperature(0.0)
                .build();
    }

    // armazenamento de memoria.
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.withMaxMessages(30);
    }

    // Configuração do serviço
    @Bean
    public AiAssistantService assistantService(ChatMemoryProvider memory, ChatModel model,
                                               MenuToolsService menuTools, BudgetToolService budgetTools) {

        return AiServices.builder(AiAssistantService.class)
                .chatModel(model)
                .chatMemoryProvider(memory)
                .tools(menuTools, budgetTools)
                .build();
    }


//    @Value("${google-ai-gemini.chat-model.api-key}")
//    private String apiKey;
//    @Value("${google-ai-gemini.chat-model.model-name}")
//    private String modelName;
//
//
//    @Bean
//    public GoogleAiGeminiChatModel getGeminiModel() {
//
//        return GoogleAiGeminiChatModel.builder()
//                .allowGoogleSearch(false)
//                .modelName(modelName)
//                .temperature(0.0)
//                .returnThinking(false)
//                .apiKey(apiKey)
//                .build();
//    }
}
