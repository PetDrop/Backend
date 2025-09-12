package com.example.petdrop.model;

import java.util.Map;

public class ReminderRequest {
    private String id;

    private String expoPushToken;
    private String title;
    private String body;
    private Map<String, Object> data;

    private String nextLocalRun;
    private String finalLocalRun;
    private String zoneId;
    private long repeatInterval;

    public ReminderRequest(String id, String expoPushToken, String title, String body, Map<String, Object> data,
            String nextLocalRun, String finalLocalRun, String zoneId, long repeatInterval) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextLocalRun = nextLocalRun;
        this.finalLocalRun = finalLocalRun;
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

    public String getNextLocalRun() {
        return nextLocalRun;
    }

    public void setNextLocalRun(String nextLocalRun) {
        this.nextLocalRun = nextLocalRun;
    }

    public String getFinalLocalRun() {
        return finalLocalRun;
    }

    public void setFinalLocalRun(String finalLocalRun) {
        this.finalLocalRun = finalLocalRun;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
