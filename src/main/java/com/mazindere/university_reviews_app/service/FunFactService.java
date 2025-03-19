package com.mazindere.university_reviews_app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;


@Service
public class FunFactService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    public String generateFunFact(String universityName) {
        RestTemplate restTemplate = new RestTemplate();
        String prompt = "Give me a fun and interesting fact about " + universityName + ".";

        // Prepare request payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.3-70b-versatile");

        requestBody.put("messages", List.of(
                Map.of("role", "system", "content",
                        "You are a highly accurate AI assistant. Only provide factual and verifiable information. If uncertain," +
                                " respond with 'click me again' instead of making up facts."),
                Map.of("role", "user", "content", prompt)
        ));

        requestBody.put("temperature", 0.3); //low-randomness & predictability
        requestBody.put("frequency_penalty", 0.2); //avoids word repetition
        requestBody.put("presence_penalty", 0.1); //encourages new responses-diversity
      //  requestBody.put("top_p", 0.7); //selection of high confidence words

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // Add API Key

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Send request to Groq API
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // Extract the fun fact from API response
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("choices")) {
            Map<String, Object> firstChoice = (Map<String, Object>) ((List<?>) responseBody.get("choices")).get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message"); //actual response text
            return (String) message.get("content");
        }
        return "No fun fact available at the moment.";
    }
}
