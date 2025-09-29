package com.example.petdrop.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class NotificationRequest {
    private String id;

    private String expoPushToken;
    private String title;
    private String body;
    private Map<String, Object> data;

    private LocalDateTime[] nextLocalRuns;
    private LocalDateTime[] finalLocalRuns;
    private ZoneId zoneId;
    private long repeatInterval;

    public NotificationRequest(String id, String expoPushToken, String title, String body, Map<String, Object> data,
            LocalDateTime[] nextLocalRuns, LocalDateTime[] finalLocalRuns, ZoneId zoneId, long repeatInterval) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextLocalRuns = nextLocalRuns;
        this.finalLocalRuns = finalLocalRuns;
        this.zoneId = zoneId;
        this.repeatInterval = repeatInterval;
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

    public LocalDateTime[] getNextLocalRuns() {
        return nextLocalRuns;
    }

    public void setNextLocalRuns(LocalDateTime[] nextLocalRuns) {
        this.nextLocalRuns = nextLocalRuns;
    }

    public LocalDateTime[] getFinalLocalRuns() {
        return finalLocalRuns;
    }

    public void setFinalLocalRuns(LocalDateTime[] finalLocalRuns) {
        this.finalLocalRuns = finalLocalRuns;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
