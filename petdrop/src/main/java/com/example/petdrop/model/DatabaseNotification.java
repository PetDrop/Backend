package com.example.petdrop.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notification")
public class DatabaseNotification extends Notification {
    @Id
    private String id;

    private String expoPushToken; // device token
    private String title;
    private String body;
    private Map<String, Object> data;

    private Instant[] nextRuns; // when to send next
    private Instant[] finalRuns; // when to send last
    private long repeatInterval; // minutes between each notification, 0 if one-time

    @Override
    public String toString() {
        return "";
    }

    @Override
    public DatabaseNotification makeIntoDBNotif() {
        return this;
    }

    // ---------- Constructors ----------
    public DatabaseNotification() {
        super();
    }

    public DatabaseNotification(String id, String expoPushToken, String title, String body, Map<String, Object> data,
                                ZonedDateTime[] nextRuns, ZonedDateTime[] finalRuns, long repeatInterval) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextRuns = zonedToInstants(nextRuns);
        this.finalRuns = zonedToInstants(finalRuns);
        this.repeatInterval = repeatInterval;
    }

    public DatabaseNotification(NotificationRequest notificationRequest, ZonedDateTime[] nextRuns, ZonedDateTime[] finalRuns) {
        this.id = notificationRequest.getId();
        this.expoPushToken = notificationRequest.getExpoPushToken();
        this.title = notificationRequest.getTitle();
        this.body = notificationRequest.getBody();
        this.data = notificationRequest.getData();
        this.nextRuns = zonedToInstants(nextRuns);
        this.finalRuns = zonedToInstants(finalRuns);
        this.repeatInterval = notificationRequest.getRepeatInterval();
    }

    // ---------- Helper conversion methods ----------
    public static Instant[] zonedToInstants(ZonedDateTime[] zonedDateTimes) {
        if (zonedDateTimes == null) return null;
        Instant[] instants = new Instant[zonedDateTimes.length];
        for (int i = 0; i < zonedDateTimes.length; i++) {
            instants[i] = zonedDateTimes[i].toInstant();
        }
        return instants;
    }

    public static ZonedDateTime[] instantsToZoned(Instant[] instants) {
        if (instants == null) return null;
        ZonedDateTime[] zoned = new ZonedDateTime[instants.length];
        for (int i = 0; i < instants.length; i++) {
            zoned[i] = instants[i].atZone(ZoneId.systemDefault());
        }
        return zoned;
    }

    // ---------- Getters & Setters ----------
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

    // ---------- Convenience methods for ZonedDateTime conversion ----------
    public ZonedDateTime[] getNextRunsZoned() {
        return instantsToZoned(nextRuns);
    }

    public ZonedDateTime[] getFinalRunsZoned() {
        return instantsToZoned(finalRuns);
    }
}
