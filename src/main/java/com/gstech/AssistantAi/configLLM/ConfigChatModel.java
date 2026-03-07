package com.gstech.AssistantAi.configLLM;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ConfigChatModel {


    @Value("${ollama-ai.chat-model.base.url}")
    private String baseUrl;
    @Value("${ollama-ai.chat-model.model-name}")
    private String modelName;
    private int timeout = 120;
    @Bean
    public ChatModel ollamaModel() {

        return OpenAiChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(timeout))
                .apiKey("")
                .build();

    }

    @Bean
    public InMemoryChatMemoryStore chatMemory() {
        return new InMemoryChatMemoryStore();
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
//                .apiKey(apiKey)
//                .build();
//    }
}
