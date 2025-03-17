package com.example.petdrop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.Pet;
import com.example.petdrop.repository.PetRepository;

@RestController
public class PetController {
    
    @Autowired 
    private PetRepository petRepo;
    
    // save pet to db
    @PostMapping("/addpet")
    public Pet addPet(@RequestBody Pet pet) {
        return petRepo.save(pet);
    }

    // use PUT for updating whole pets
    // updatedPet must have all fields, not just ones to be updated
    @PutMapping("/updatepet")
    public ResponseEntity<Pet> updatePet(@RequestBody Pet updatedPet) {
        Pet savedPet = petRepo.save(updatedPet);
        return ResponseEntity.ok(savedPet);
    }

    // update a pet's username
    @PatchMapping("/updatepet/username/{id}")
    public long updatePetUsername(@PathVariable String id, @RequestBody String username) {
        return petRepo.updatePetName(id, username);
    }

    // update a pet's email
    @PatchMapping("/updatepet/email/{id}")
    public long updatePetEmail(@PathVariable String id, @RequestBody String email) {
        return petRepo.updatePetImage(id, email);
    }

    // update a pet's password
    @PatchMapping("/updatepet/password/{id}")
    public long updatePetPassword(@PathVariable String id, @RequestBody String password) {
        return petRepo.updatePetAge(id, password);
    }

    // update a pet's phone number
    @PatchMapping("/updatepet/phone/{id}")
    public long updatePetPhone(@PathVariable String id, @RequestBody String phone) {
        return petRepo.updatePetBreed(id, phone);
    }

    // update a pet's address
    @PatchMapping("/updatepet/address/{id}")
    public long updatePetAddress(@PathVariable String id, @RequestBody String address) {
        return petRepo.updatePetAddress(id, address);
    }

    // get all pets from db
    @GetMapping("/getallpets")
    public List<Pet> getAllPets() {
        return petRepo.findAll(); 
    }

    // get pet from db using its id
    @GetMapping("/getpetbyid/{id}")
    public Optional<Pet> getPetById(@PathVariable String id) {
        return petRepo.findById(id);
    }
    
}
