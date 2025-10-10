package com.example.petdrop.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("medication")
public class Medication {

    @Id
    private String id;

    private String name;
    private String color;
    private String description;

    @DocumentReference(collection = "notification")
    private List<Notification> notifications;

    private int range;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("id: " + id + "\n");
        result.append("name: " + name + "\n");
        result.append("color: " + color + "\n");
        result.append("desc: " + description + "\n");
        result.append("notifications: [");
        if (!(notifications == null)) {
            result.append("\n");
            for (Notification notif : notifications) {
                result.append(notif.toString() + ",\n\n");
            }
        }
        result.append("]\n");
        result.append("range: " + range);
        return result.toString();
    }

    public Medication(String id, String name, String color, String description, List<Notification> notifications,
            int range) {
        super();
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.notifications = notifications;
        this.range = range;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public int getRange() {
        return range;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setRange(int range) {
        this.range = range;
    }
}