package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("account")
public class Account {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;
    private String[] sharedUsers;

    @DocumentReference(collection = "pet")
    private Pet[] pets;

    @DocumentReference(collection = "reminder")
    private Reminder[] reminders;
    
    public Account(String id, String username, String email, String password, String[] sharedUsers, Pet[] pets, Reminder[] reminders) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.sharedUsers = sharedUsers;
        this.pets = pets;
        this.reminders = reminders;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String[] getSharedUsers() {
        return sharedUsers;
    }

    public Pet[] getPets() {
        return pets;
    }

    public Reminder[] getReminders() {
        return reminders;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSharedUsers(String[] sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public void setPets(Pet[] pets) {
        this.pets = pets;
    }

    public void setReminders(Reminder[] reminders) {
        this.reminders = reminders;
    }
}