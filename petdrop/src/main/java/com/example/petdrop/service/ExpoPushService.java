package com.example.petdrop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.petdrop.model.Notification;

@Service
public class ExpoPushService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final int MAX_BATCH_SIZE = 100;
    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    /**
     * Send a single notification to multiple recipients
     * @param notification The notification to send
     * @param pushTokens List of expo push tokens to send to
     */
    public void sendPushToMultipleRecipients(Notification notification, List<String> pushTokens) {
        List<Map<String, Object>> messages = new ArrayList<>();
        
        // Create a message for each recipient
        for (String token : pushTokens) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("to", token);
            payload.put("title", notification.getTitle());
            payload.put("body", notification.getBody());
            payload.put("data", notification.getData());
            messages.add(payload);
        }

        // Send in batches of MAX_BATCH_SIZE
        for (int i = 0; i < messages.size(); i += MAX_BATCH_SIZE) {
            int end = Math.min(i + MAX_BATCH_SIZE, messages.size());
            List<Map<String, Object>> chunk = messages.subList(i, end);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(chunk, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_URL, request, String.class);
                System.out.println("Expo response: " + response.getBody());
            } catch (Exception e) {
                System.err.println("Failed to send batch: " + e.getMessage());
            }
        }
    }

    @Deprecated
    public void sendPushBatch(List<Notification> notifications) {
        // This method is deprecated - notifications should use sendPushToMultipleRecipients instead
        System.err.println("Warning: sendPushBatch is deprecated. Use sendPushToMultipleRecipients instead.");
    }
}
