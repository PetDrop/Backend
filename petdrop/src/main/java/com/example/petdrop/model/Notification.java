package com.example.petdrop.model;

import java.time.Instant;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notification")
public class Notification {
    @Id
    private String id;

    private String ownerUsername; // username of the pet owner
    private String title;
    private String body;
    private Map<String, Object> data;

    private Instant[] nextRuns; // when to send next
    private Instant[] finalRuns; // when to send last
    private String repeatInterval; // "daily", "weekly", "monthly", or "" for one-time

    public Notification() {
        super();
    }

    public Notification(String id, String ownerUsername, String title, String body, Map<String, Object> data,
                                Instant[] nextRuns, Instant[] finalRuns, String repeatInterval) {
        this.id = id;
        this.ownerUsername = ownerUsername;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextRuns = nextRuns;
        this.finalRuns = finalRuns;
        this.repeatInterval = repeatInterval;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
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

    public Instant[] getNextRuns() {
        return nextRuns;
    }

    public void setNextRuns(Instant[] nextRuns) {
        this.nextRuns = nextRuns;
    }

    public Instant[] getFinalRuns() {
        return finalRuns;
    }

    public void setFinalRuns(Instant[] finalRuns) {
        this.finalRuns = finalRuns;
    }

    public String getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(String repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
