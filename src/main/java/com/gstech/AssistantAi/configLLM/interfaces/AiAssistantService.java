package com.gstech.AssistantAi.configLLM.interfaces;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface AiAssistantService {

    String message(String message);
}
