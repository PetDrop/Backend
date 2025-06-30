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
    private String[] sharedUsers; // users they request info from
    private String[] usersSharedWith; // users they volunteer their info to

    @DocumentReference(collection = "pet")
    private Pet[] pets;

    public Account(String id, String username, String email, String password, String[] sharedUsers, String[] usersSharedWith, Pet[] pets) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.sharedUsers = sharedUsers;
        this.usersSharedWith = usersSharedWith;
        this.pets = pets;
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

    public String[] getUsersSharedWith() {
        return usersSharedWith;
    }

    public Pet[] getPets() {
        return pets;
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

    public void setUsersSharedWith(String[] usersSharedWith) {
        this.usersSharedWith = usersSharedWith;
    }

    public void setPets(Pet[] pets) {
        this.pets = pets;
    }
}