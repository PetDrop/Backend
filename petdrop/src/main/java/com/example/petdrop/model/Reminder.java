package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("reminder")
public class Reminder {

    @Id
    private String id;

    @DocumentReference(collection = "medication")
    private Medication medication;

    @DocumentReference(collection = "pet")
    private Pet pet;

    private String[] notifications;
    
    public Reminder(String id, Medication medication, Pet pet, String[] notifications) {
        super();
        this.id = id;
        this.medication = medication;
        this.pet = pet;
        this.notifications = notifications;
    }

    public String getId() {
        return id;
    }

    public Medication getMedication() {
        return medication;
    }

    public Pet getPet() {
        return pet;
    }

    public String[] getNotifications() {
        return notifications;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setNotifications(String[] notifications) {
        this.notifications = notifications;
    }
}