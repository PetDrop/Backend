package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("reminder")
public class Reminder {

    @Id
    private String id;

    private String[] notifications;
    
    public Reminder(String id, String[] notifications) {
        super();
        this.id = id;
        this.notifications = notifications;
    }

    public String getId() {
        return id;
    }

    public String[] getNotifications() {
        return notifications;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNotifications(String[] notifications) {
        this.notifications = notifications;
    }
}