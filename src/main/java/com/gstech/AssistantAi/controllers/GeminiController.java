package com.gstech.AssistantAi.controllers;

import com.gstech.AssistantAi.configLLM.interfaces.AiAssistantService;
import com.gstech.AssistantAi.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GeminiController {

    @Autowired
    private AiAssistantService assistant;

    @PostMapping("/chat")
    public ResponseEntity<ResponseDTO> getResponse(@RequestBody ResponseDTO message) {

        String response = assistant.message(message.message());
        return ResponseEntity.ok(new ResponseDTO(response));
    }
}
