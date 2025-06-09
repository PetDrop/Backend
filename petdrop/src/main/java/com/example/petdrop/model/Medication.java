package com.example.petdrop.model;

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
    private DateObj[] dates;

    @DocumentReference(collection = "reminder")
    private Reminder reminder;

    private int range;
    
    public Medication(String id, String name, String color, String description, DateObj[] dates, Reminder reminder, int range) {
        super();
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.dates = dates;
        this.reminder = reminder;
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

    public DateObj[] getDates() {
        return dates;
    }

    public Reminder getReminder() {
        return reminder;
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

    public void setDates(DateObj[] dates) {
        this.dates = dates;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public void setRange(int range) {
        this.range = range;
    }
}