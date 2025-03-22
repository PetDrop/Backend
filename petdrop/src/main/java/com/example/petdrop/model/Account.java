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
    private String phone;
    private String address;
    private String[] emergencyContacts;

    @DocumentReference(collection = "pet")
    private Pet[] pets;

    @DocumentReference(collection = "reminder")
    private Reminder[] reminders;
    
    public Account(String id, String username, String email, String password, String phone, String address, String[] emergencyContacts, Pet[] pets, Reminder[] reminders) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.emergencyContacts = emergencyContacts;
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

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String[] getEmergencyContacts() {
        return emergencyContacts;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmergencyContacts(String[] emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }

    public void setPets(Pet[] pets) {
        this.pets = pets;
    }

    public void setReminders(Reminder[] reminders) {
        this.reminders = reminders;
    }
}