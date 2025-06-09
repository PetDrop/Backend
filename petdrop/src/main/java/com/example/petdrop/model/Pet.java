package com.example.petdrop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document("pet")
public class Pet {

    @Id
    private String id;

    private String name;
    private String image;
    private int age;
    private String breed;
    private String address;
    private String vet;
    private String vetPhone;

    @DocumentReference(collection = "medication")
    private Medication[] medications;
    
    public Pet(String id, String name, String image, int age, String breed, String address, String vet, String vetPhone, Medication[] medications) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.age = age;
        this.breed = breed;
        this.address = address;
        this.vet = vet;
        this.vetPhone = vetPhone;
        this.medications = medications;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getAddress() {
        return address;
    }

    public String getVet() {
        return vet;
    }

    public String getVetPhone() {
        return vetPhone;
    }

    public Medication[] getMedications() {
        return medications;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setVet(String vet) {
        this.vet = vet;
    }

    public void setVetPhone(String vetPhone) {
        this.vetPhone = vetPhone;
    }

    public void setMedications(Medication[] medications) {
        this.medications = medications;
    }
}