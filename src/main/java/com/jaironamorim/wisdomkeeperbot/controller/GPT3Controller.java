package com.jaironamorim.wisdomkeeperbot.controller;

import com.jaironamorim.wisdomkeeperbot.service.Gpt3Service;
import com.theokanning.openai.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GPT3Controller {

    private final Gpt3Service service;

    @PostMapping("/gpt3/generate-answer")
    public ResponseEntity<String> generateAnswer(@RequestBody String question) {
        String answer = service.generateResponse(question, "USER");
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }
}