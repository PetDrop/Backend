package com.example.petdrop.model;

import java.time.ZonedDateTime;
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

    private ZonedDateTime[] nextRuns; // when to send next
    private ZonedDateTime[] finalRuns; // when to send last
    private long repeatInterval; // minutes between each time notif is sent, 0 if one-time

    public Reminder() {
        super();
    }

    public Reminder(String id, String expoPushToken, String title, String body, Map<String, Object> data,
            ZonedDateTime[] nextRuns, ZonedDateTime[] lastRuns, long repeatInterval) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextRuns = nextRuns;
        this.finalRuns = lastRuns;
        this.repeatInterval = repeatInterval;
    }

    public Reminder(ReminderRequest reminderRequest, ZonedDateTime[] nextRuns, ZonedDateTime[] finalRuns) {
        this.id = reminderRequest.getId();
        this.expoPushToken = reminderRequest.getExpoPushToken();
        this.title = reminderRequest.getTitle();
        this.body = reminderRequest.getBody();
        this.data = reminderRequest.getData();
        this.nextRuns = nextRuns;
        this.finalRuns = finalRuns;
        this.repeatInterval = reminderRequest.getRepeatInterval();
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

    public ZonedDateTime[] getNextRuns() {
        return nextRuns;
    }

    public void setNextRuns(ZonedDateTime[] nextRuns) {
        this.nextRuns = nextRuns;
    }

    public ZonedDateTime[] getFinalRuns() {
        return finalRuns;
    }

    public void setFinalRuns(ZonedDateTime[] finalRuns) {
        this.finalRuns = finalRuns;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }
}
