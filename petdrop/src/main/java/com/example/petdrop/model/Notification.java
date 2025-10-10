package com.example.petdrop.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,      // Use a type name, not a class name
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",                // The field name in JSON that tells which subtype to use
    visible = false
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DatabaseNotification.class, name = "database"),
    @JsonSubTypes.Type(value = NotificationRequest.class, name = "request")
})


public abstract class Notification {
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

    public static List<DatabaseNotification> makeIntoDBNotifsList(Notification[] notifs) {
        if (notifs == null) {
            return new ArrayList<>();
        }

        List<DatabaseNotification> dbNotifs = new ArrayList<>(notifs.length);

        for (int i = 0; i < notifs.length; i++) {
            dbNotifs.add(notifs[i].makeIntoDBNotif());
        }

        return dbNotifs;
    }

    public static DatabaseNotification[] makeIntoDBNotifsArr(Notification[] notifs) {
        if (notifs == null) {
            return new DatabaseNotification[0];
        }

        DatabaseNotification[] dbNotifs = new DatabaseNotification[notifs.length];

        for (int i = 0; i < notifs.length; i++) {
            dbNotifs[i] = notifs[i].makeIntoDBNotif();
        }

        return dbNotifs;
    }

    public abstract DatabaseNotification makeIntoDBNotif();

    public abstract String getId();

    public abstract void setId(String id);

    public abstract String getExpoPushToken();

    public abstract void setExpoPushToken(String expoPushToken);

    public abstract String getTitle();

    public abstract void setTitle(String title);

    public abstract String getBody();

    public abstract void setBody(String body);

    public abstract Map<String, Object> getData();

    public abstract void setData(Map<String, Object> data);

    public abstract Instant[] getNextRuns();

    public abstract void setNextRuns(Instant[] nextRuns);

    public abstract Instant[] getFinalRuns();

    public abstract void setFinalRuns(Instant[] finalRuns);

    public abstract long getRepeatInterval();

    public abstract void setRepeatInterval(long repeatInterval);
}
