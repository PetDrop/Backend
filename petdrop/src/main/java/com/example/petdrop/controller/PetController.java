package com.example.petdrop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.Medication;
import com.example.petdrop.model.Pet;
import com.example.petdrop.repository.MedicationRepository;
import com.example.petdrop.repository.PetRepository;

@RestController
public class PetController {
    
    @Autowired 
    private PetRepository petRepo;

    @Autowired
    private MedicationRepository medicationRepo;
    
    // save pet to db
    @PostMapping("/add-pet")
    public Pet addPet(@RequestBody Pet pet) {
        return petRepo.save(pet);
    }

    // use PUT for updating whole pets
    // updatedPet must have all fields, not just ones to be updated
    @PutMapping("/update-pet")
    public ResponseEntity<Pet> updatePet(@RequestBody Pet updatedPet) {
        Pet savedPet = petRepo.save(updatedPet);
        return ResponseEntity.ok(savedPet);
    }

    // delete pet from db using its id
    @DeleteMapping("/delete-pet/{id}")
    public void deletePetById(@PathVariable String id) {
        // delete all of the pet's meds
        Medication[] meds = getPetById(id).get().getMedications();
        for (int i = 0; i < meds.length; i++) {
            medicationRepo.delete(meds[i]);
        }
        petRepo.deleteById(id);
    }

    // get pet from db using its id
    @GetMapping("/get-pet/{id}")
    public Optional<Pet> getPetById(@PathVariable String id) {
        return petRepo.findById(id);
    }

    // get all pets from db
    @GetMapping("/get-all-pets")
    public List<Pet> getAllPets() {
        return petRepo.findAll(); 
    }
    
}
