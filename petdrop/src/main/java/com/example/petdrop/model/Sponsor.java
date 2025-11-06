package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sponsor")
public class Sponsor {

    @Id
    private String id;

    private String name;
    private String description;
    private String image;

    public Sponsor(String id, String name, String description, String image) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

}