package com.example.petdrop.dto;

import java.time.Instant;
import java.util.Map;

import org.springframework.data.annotation.Transient;

public class Notification {
    @Transient
    private String id;

    @Transient
    private String expoPushToken; // device token
    @Transient
    private String title;
    @Transient
    private String body;
    @Transient
    private Map<String, Object> data;

    @Transient
    private Instant[] nextRuns; // when to send next
    @Transient
    private Instant[] finalRuns; // when to send last
    @Transient
    private long repeatInterval; // minutes between each time notif is sent, 0 if one-time

    public Notification() {
        super();
    }

    public Notification(String id, String expoPushToken, String title, String body, Map<String, Object> data,
            Instant[] nextRuns, Instant[] lastRuns, long repeatInterval) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextRuns = nextRuns;
        this.finalRuns = lastRuns;
        this.repeatInterval = repeatInterval;
    }

    public Notification(NotificationRequest notificationRequest, Instant[] nextRuns, Instant[] finalRuns) {
        this.id = notificationRequest.getId();
        this.expoPushToken = notificationRequest.getExpoPushToken();
        this.title = notificationRequest.getTitle();
        this.body = notificationRequest.getBody();
        this.data = notificationRequest.getData();
        this.nextRuns = nextRuns;
        this.finalRuns = finalRuns;
        this.repeatInterval = notificationRequest.getRepeatInterval();
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

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
