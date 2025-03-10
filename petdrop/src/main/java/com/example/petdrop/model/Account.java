package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("account")
public class Account {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;
    private String address;
    private String[] emergencyContacts;
    
    public Account(String id, String username, String email, String password, String address, String[] emergencyContacts) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.emergencyContacts = emergencyContacts;
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

    public String getAddress() {
        return address;
    }

    public String[] getEmergencyContacts() {
        return emergencyContacts;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmergencyContacts(String[] emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }
}