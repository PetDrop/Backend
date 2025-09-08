package com.example.petdrop.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reminder")
public class Reminder {
    @Id
    private String id;

    private String expoPushToken; // device token
    private String title;
    private String body;
    private Map<String, Object> data;

    private LocalDateTime nextRun; // when to send next
    private LocalDateTime lastRun; // when to send last
    private Duration repeatInterval; // null if one-time

    private boolean active;

    public Reminder(String id, String expoPushToken, String title, String body, Map<String, Object> data,
            LocalDateTime nextRun, LocalDateTime lastRun, Duration repeatInterval, boolean active) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextRun = nextRun;
        this.lastRun = lastRun;
        this.repeatInterval = repeatInterval;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpoPushToken() {
        return expoPushToken;
    }

    public void setExpoPushToken(String expoPushToken) {
        this.expoPushToken = expoPushToken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public LocalDateTime getNextRun() {
        return nextRun;
    }

    public void setNextRun(LocalDateTime nextRun) {
        this.nextRun = nextRun;
    }

    public LocalDateTime getLastRun() {
        return lastRun;
    }

    public void setLastRun(LocalDateTime lastRun) {
        this.lastRun = lastRun;
    }

    public Duration getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Duration repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
