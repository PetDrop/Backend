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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.petdrop.model.Account;
import com.example.petdrop.model.Notification;
import com.example.petdrop.repository.AccountRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExpoPushService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final int MAX_BATCH_SIZE = 100;
    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";
    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_RETRY_DELAY_MS = 1000; // 1 second
    private static final double BACKOFF_MULTIPLIER = 5.0;

    public ExpoPushService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

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
            List<String> chunkTokens = pushTokens.subList(i, end);

            sendBatchWithRetry(chunk, chunkTokens);
        }
    }

    /**
     * Send a batch of messages with retry logic and exponential backoff
     * @param batch The batch of messages to send
     * @param tokens The corresponding push tokens for this batch
     */
    private void sendBatchWithRetry(List<Map<String, Object>> batch, List<String> tokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(batch, headers);

        int attempt = 0;
        long delayMs = INITIAL_RETRY_DELAY_MS;

        while (attempt <= MAX_RETRIES) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(EXPO_PUSH_URL, request, String.class);
                
                // Check if response indicates success
                if (response.getStatusCode().is2xxSuccessful()) {
                    // Parse response to check for DeviceNotRegistered errors
                    handleExpoResponse(response.getBody(), tokens);
                    return; // Success, exit retry loop
                } else if (response.getStatusCode().is4xxClientError()) {
                    // Client errors (4xx) should not be retried
                    System.err.println("Client error sending batch (HTTP " + response.getStatusCode() + "): " + response.getBody());
                    return;
                } else {
                    // Server errors (5xx) should be retried
                    throw new HttpServerErrorException(response.getStatusCode(), "Server error: " + response.getBody());
                }
            } catch (HttpServerErrorException e) {
                // 5xx server errors - retry
                attempt++;
                if (attempt > MAX_RETRIES) {
                    System.err.println("Failed to send batch after " + MAX_RETRIES + " retries. HTTP " + e.getStatusCode() + ": " + e.getMessage());
                    return;
                }
                System.err.println("Server error sending batch (HTTP " + e.getStatusCode() + "). Retrying in " + delayMs + "ms (attempt " + attempt + "/" + MAX_RETRIES + ")");
                sleep(delayMs);
                delayMs = (long) (delayMs * BACKOFF_MULTIPLIER);
            } catch (ResourceAccessException e) {
                // Network/connection errors - retry
                attempt++;
                if (attempt > MAX_RETRIES) {
                    System.err.println("Failed to send batch after " + MAX_RETRIES + " retries due to network error: " + e.getMessage());
                    return;
                }
                System.err.println("Network error sending batch. Retrying in " + delayMs + "ms (attempt " + attempt + "/" + MAX_RETRIES + "): " + e.getMessage());
                sleep(delayMs);
                delayMs = (long) (delayMs * BACKOFF_MULTIPLIER);
            } catch (RestClientException e) {
                // Other REST client errors - retry
                attempt++;
                if (attempt > MAX_RETRIES) {
                    System.err.println("Failed to send batch after " + MAX_RETRIES + " retries: " + e.getMessage());
                    return;
                }
                System.err.println("Error sending batch. Retrying in " + delayMs + "ms (attempt " + attempt + "/" + MAX_RETRIES + "): " + e.getMessage());
                sleep(delayMs);
                delayMs = (long) (delayMs * BACKOFF_MULTIPLIER);
            } catch (Exception e) {
                // Unexpected errors - log and don't retry
                System.err.println("Unexpected error sending batch: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * Parse Expo API response and handle DeviceNotRegistered errors
     * @param responseBody The JSON response body from Expo
     * @param tokens The list of tokens that were sent in this batch
     */
    private void handleExpoResponse(String responseBody, List<String> tokens) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode dataNode = rootNode.get("data");
            
            if (dataNode != null && dataNode.isArray()) {
                for (int i = 0; i < dataNode.size() && i < tokens.size(); i++) {
                    JsonNode result = dataNode.get(i);
                    String status = result.has("status") ? result.get("status").asText() : null;
                    String message = result.has("message") ? result.get("message").asText() : null;
                    
                    if ("error".equals(status) && "DeviceNotRegistered".equals(message)) {
                        String invalidToken = tokens.get(i);
                        System.err.println("DeviceNotRegistered error for token: " + invalidToken);
                        removeInvalidToken(invalidToken);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse Expo response: " + e.getMessage());
            // Log but don't fail - response parsing is best effort
        }
    }

    /**
     * Remove an invalid push token from all accounts that have it
     * @param invalidToken The push token to remove
     */
    private void removeInvalidToken(String invalidToken) {
        try {
            // Find all accounts with this token
            List<Account> accounts = accountRepository.findAccountsByPushToken(invalidToken);
            
            for (Account account : accounts) {
                account.setExpoPushToken(null);
                accountRepository.save(account);
                System.out.println("Removed invalid push token from account: " + account.getUsername());
            }
        } catch (Exception e) {
            System.err.println("Failed to remove invalid token: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sleep for the specified number of milliseconds
     * @param milliseconds The number of milliseconds to sleep
     */
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Sleep interrupted: " + e.getMessage());
        }
    }

    @Deprecated
    public void sendPushBatch(List<Notification> notifications) {
        // This method is deprecated - notifications should use sendPushToMultipleRecipients instead
        System.err.println("Warning: sendPushBatch is deprecated. Use sendPushToMultipleRecipients instead.");
    }
}
