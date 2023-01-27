package com.jaironamorim.wisdomkeeperbot.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Gpt3Service {
    private final OpenAiService service;
    private Map<String, StringBuilder> promptStorage = new HashMap<>();
    public String generateResponse(String question, String chatID) {


        if (Objects.isNull(promptStorage.get(chatID))) {
            promptStorage.put(chatID, new StringBuilder().append(chatID).append(question));
        }

        promptStorage.get(chatID).append(chatID).append(question);
        promptStorage.put(chatID, promptStorage.get(chatID).append(chatID).append(question));

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(promptStorage.get(chatID).toString())
                .temperature(0.9)
                .maxTokens(150) //3177
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.6)
                .stop(Arrays.asList(chatID, " AI:"))
                .logprobs(0)
                .bestOf(1)
                .stream(false)
                .build();

        CompletionResult completionResult = service.createCompletion(completionRequest);
        String response = completionResult.getChoices().get(0).getText();
        promptStorage.put(chatID, promptStorage.get(chatID).append(response.trim()));

        return response;
    }
}
