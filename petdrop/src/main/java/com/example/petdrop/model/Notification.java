package com.example.petdrop.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification")
public class Notification {
    @Id
    private String id;

    private String expoPushToken; // device token
    private String title;
    private String body;
    private Map<String, Object> data;

    private LocalDateTime nextRun; // when to send next
    private Duration repeatInterval; // null if one-time
    private Integer remainingRepeats; // how many left, null for infinite

    private boolean active;

    public Notification(String id, String expoPushToken, String title, String body, Map<String, Object> data,
            LocalDateTime nextRun, Duration repeatInterval, Integer remainingRepeats, boolean active) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextRun = nextRun;
        this.repeatInterval = repeatInterval;
        this.remainingRepeats = remainingRepeats;
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

    public Duration getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(Duration repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public Integer getRemainingRepeats() {
        return remainingRepeats;
    }

    public void setRemainingRepeats(Integer remainingRepeats) {
        this.remainingRepeats = remainingRepeats;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
