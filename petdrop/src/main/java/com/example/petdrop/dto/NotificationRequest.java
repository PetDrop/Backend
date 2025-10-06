package com.example.petdrop.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Transient;

import com.example.petdrop.model.DatabaseNotification;

public class NotificationRequest extends Notification {
    @Transient
    private String id;

    @Transient
    private String expoPushToken;
    @Transient
    private String title;
    @Transient
    private String body;
    @Transient
    private Map<String, Object> data;

    @Transient
    private LocalDateTime[] nextLocalRuns;
    @Transient
    private LocalDateTime[] finalLocalRuns;
    @Transient
    private ZoneId zoneId;
    @Transient
    private long repeatInterval;

    public static DatabaseNotification makeIntoDBNotif(NotificationRequest notifReq) {
        // create variables to avoid repetetive getter calls
        LocalDateTime[] nextLocalRuns = notifReq.getNextLocalRuns();
        LocalDateTime[] finalLocalRuns = notifReq.getFinalLocalRuns();
        ZoneId zoneId = notifReq.getZoneId();

        // instantiate arrays to be populated
        ZonedDateTime[] nextRuns = new ZonedDateTime[nextLocalRuns.length];
        ZonedDateTime[] finalRuns = new ZonedDateTime[finalLocalRuns.length];

        // get ZonedDateTime from each LocalDateTime formatted string using zoneIds
        // (arrays must be kept consistent to avoid errors here)
        for (int i = 0; i < nextRuns.length; i++) {
            nextRuns[i] = ZonedDateTime.of(nextLocalRuns[i], zoneId);
            nextRuns[i] = ZonedDateTime.of(finalLocalRuns[i], zoneId);
        }

        return new DatabaseNotification(notifReq, nextRuns, finalRuns);
    }

    // redundant function used to allow for polymorphism
    public static List<DatabaseNotification> makeIntoDBNotif(DatabaseNotification[] databaseNotifications) {
        return Arrays.asList(databaseNotifications);
    }

    public static List<DatabaseNotification> makeIntoDBNotif(NotificationRequest[] notificationRequests) {
        ArrayList<DatabaseNotification> notifs = new ArrayList<DatabaseNotification>();

        // turn each notifReq into a Notification
        for (NotificationRequest notifReq : notificationRequests) {
            notifs.add(makeIntoDBNotif(notifReq));
        }

        return notifs;
    }

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

    public String get_expoPushToken() {
        return expoPushToken;
    }

    public void set_expoPushToken(String expoPushToken) {
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
