package com.gstech.AssistantAi.controllers;

import com.gstech.AssistantAi.configLLM.interfaces.AiAssistantService;
import com.gstech.AssistantAi.dto.RequestDTO;
import com.gstech.AssistantAi.dto.ResponseLLMDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LLModelController {

    @Autowired
    private AiAssistantService assistant;


    @PostMapping("/chat")
    public ResponseEntity<ResponseLLMDTO> getResponse(@RequestBody RequestDTO message) {

        String conversationId = message.id();

        if (conversationId == null || conversationId.isBlank()) {
            conversationId = UUID.randomUUID().toString();
        }


        String response = assistant.message(conversationId, message.UserMessage());
        return ResponseEntity.ok(new ResponseLLMDTO(response, conversationId));
    }
}
