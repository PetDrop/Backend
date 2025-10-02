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

import com.example.petdrop.model.DatabaseNotification;

@Service
public class ExpoPushService {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final int MAX_BATCH_SIZE = 100;
    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    public void sendPushBatch(List<DatabaseNotification> notifications) {
        // break into chunks of MAX_BATCH_SIZE
        for (int i = 0; i < notifications.size(); i += MAX_BATCH_SIZE) {
            int end = Math.min(i + MAX_BATCH_SIZE, notifications.size());
            List<DatabaseNotification> chunk = notifications.subList(i, end);

            List<Map<String, Object>> messages = new ArrayList<>();
            for (DatabaseNotification n : chunk) {
                Map<String, Object> payload = new HashMap<>();
                payload.put("to", n.getExpoPushToken());
                payload.put("title", n.getTitle());
                payload.put("body", n.getBody());
                payload.put("data", n.getData());
                messages.add(payload);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(messages, headers);

            try {
                ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_URL, request, String.class);
                System.out.println("Expo response: " + response.getBody());
            } catch (Exception e) {
                System.err.println("Failed to send batch: " + e.getMessage());
            }
        }
    }
}
