package com.example.petdrop.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.data.annotation.Transient;

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
    private long repeatInterval;
    @Transient
    private ZoneId zoneId;

    public DatabaseNotification makeIntoDBNotif() {
        // if no runs (which shouldn't ever happen technically) then return empty arrays
        if (nextLocalRuns == null || nextLocalRuns.length == 0) { // nextLocalRuns and finalLocalRuns should be same length
            return new DatabaseNotification(this, new ZonedDateTime[0], new ZonedDateTime[0]);
        }

        // instantiate arrays to be populated
        ZonedDateTime[] nextRuns = new ZonedDateTime[nextLocalRuns.length];
        ZonedDateTime[] finalRuns = new ZonedDateTime[finalLocalRuns.length];

        // get ZonedDateTime from each LocalDateTime formatted string using zoneIds
        // (arrays must be kept consistent to avoid errors here)
        for (int i = 0; i < nextRuns.length; i++) {
            nextRuns[i] = ZonedDateTime.of(nextLocalRuns[i], zoneId);
            finalRuns[i] = ZonedDateTime.of(finalLocalRuns[i], zoneId);
        }

        // use constructor to do final steps
        return new DatabaseNotification(this, nextRuns, finalRuns);
    }

    public NotificationRequest(String id, String expoPushToken, String title, String body, Map<String, Object> data,
            LocalDateTime[] nextLocalRuns, LocalDateTime[] finalLocalRuns, long repeatInterval, ZoneId zoneId) {
        this.id = id;
        this.expoPushToken = expoPushToken;
        this.title = title;
        this.body = body;
        this.data = data;
        this.nextLocalRuns = nextLocalRuns;
        this.finalLocalRuns = finalLocalRuns;
        this.repeatInterval = repeatInterval;
        this.zoneId = zoneId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getExpoPushToken() {
        return expoPushToken;
    }

    @Override
    public void setExpoPushToken(String expoPushToken) {
        this.expoPushToken = expoPushToken;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @Override
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

    @Override
    public long getRepeatInterval() {
        return repeatInterval;
    }

    @Override
    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public Instant[] getNextRuns() {
        Instant[] nextRuns = new Instant[finalLocalRuns.length];

        for (int i = 0; i < finalLocalRuns.length; i++) {
            nextRuns[i] = finalLocalRuns[i].toInstant(null);
        }

        return nextRuns;
    }

    @Override
    public void setNextRuns(Instant[] nextRuns) {}

    @Override
    public Instant[] getFinalRuns() {
        Instant[] finalRuns = new Instant[finalLocalRuns.length];

        for (int i = 0; i < finalLocalRuns.length; i++) {
            finalRuns[i] = finalLocalRuns[i].toInstant(null);
        }

        return finalRuns;
    }

    @Override
    public void setFinalRuns(Instant[] finalRuns) {}
}
