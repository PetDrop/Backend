package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sponsor_medication")
public class SponsorMedication {

    @Id
    private String id;

    private String name;
    private String[] instructions;
    private String videoLink;
    
    public SponsorMedication(String id, String name, String[] instructions, String videoLink) {
        super();
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.videoLink = videoLink;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

}