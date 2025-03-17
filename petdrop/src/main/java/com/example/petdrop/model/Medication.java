package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("medication")
public class Medication {

    @Id
    private String id;

    private String name;
    private String color;
    private String description;
    private String[] dates;
    private int range;
    
    public Medication(String id, String name, String color, String description, String[] dates, int range) {
        super();
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.dates = dates;
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

    public String[] getDates() {
        return dates;
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

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public void setRange(int range) {
        this.range = range;
    }
}