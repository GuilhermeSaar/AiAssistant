package com.gstech.AssistantAi.controllers;

import com.gstech.AssistantAi.configLLM.interfaces.AiAssistantService;
import com.gstech.AssistantAi.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LLModelController {

    @Autowired
    private AiAssistantService assistant;

    @PostMapping("/chat")
    public ResponseEntity<ResponseDTO> getResponse(@RequestBody ResponseDTO message) {

        String memoryId = message.uuid();

        if (memoryId == null || memoryId.isEmpty()) {
            memoryId = UUID.randomUUID().toString();
        }

        String response = assistant.message(memoryId, message.message());
        return ResponseEntity.ok(new ResponseDTO(response, memoryId));
    }
}
