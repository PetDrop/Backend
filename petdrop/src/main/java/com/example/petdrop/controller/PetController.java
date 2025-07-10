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
import org.springframework.web.bind.annotation.PatchMapping;
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

    // update a pet's name
    @PatchMapping("/updatepet/name/{id}")
    public long updatePetName(@PathVariable String id, @RequestBody String name) {
        return petRepo.updatePetName(id, name);
    }

    // update a pet's image
    @PatchMapping("/updatepet/image/{id}")
    public long updatePetImage(@PathVariable String id, @RequestBody String image) {
        return petRepo.updatePetImage(id, image);
    }

    // update a pet's age
    @PatchMapping("/updatepet/age/{id}")
    public long updatePetAge(@PathVariable String id, @RequestBody int age) {
        return petRepo.updatePetAge(id, age);
    }

    // update a pet's breed
    @PatchMapping("/updatepet/breed/{id}")
    public long updatePetBreed(@PathVariable String id, @RequestBody String breed) {
        return petRepo.updatePetBreed(id, breed);
    }

    // update a pet's address
    @PatchMapping("/updatepet/address/{id}")
    public long updatePetAddress(@PathVariable String id, @RequestBody String address) {
        return petRepo.updatePetAddress(id, address);
    }

    // update a pet's vet
    @PatchMapping("/updatepet/vet/{id}")
    public long updatePetVet(@PathVariable String id, @RequestBody String vet) {
        return petRepo.updatePetVet(id, vet);
    }

    // update a pet's vet's phone number
    @PatchMapping("/updatepet/vetphone/{id}")
    public long updatePetVetPhone(@PathVariable String id, @RequestBody String vetPhone) {
        return petRepo.updatePetVetPhone(id, vetPhone);
    }

    // update whether or not a pet is editable
    @PatchMapping("/updatepet/editable/{id}")
    public long updatePetEditable(@PathVariable String id, @RequestBody boolean editable) {
        return petRepo.updatePetEditable(id, editable);
    }

    // update a pet's Medications
    @PatchMapping("/updatepet/medications/{id}")
    public long updatePetAddress(@PathVariable String id, @RequestBody Medication[] medications) {
        return petRepo.updatePetMedications(id, medications);
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

    // delete pet from db using its id
    @DeleteMapping("/deletepetbyid/{id}")
    public void deletePetById(@PathVariable String id) {
        // delete all of the pet's meds
        Medication[] meds = getPetById(id).get().getMedications();
        for (int i = 0; i < meds.length; i++) {
            medicationRepo.deleteById(meds[i].getId());
        }
        petRepo.deleteById(id);
    }
    
}
