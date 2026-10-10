package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sponsor_medication")
public class SponsorMedication {

    @Id
    private String id;

    private String name;

    private boolean generic;

    public SponsorMedication(String id, String name, boolean generic) {
        super();
        this.id = id;
        this.name = name;
        this.generic = generic;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getGeneric() {
        return generic;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

}