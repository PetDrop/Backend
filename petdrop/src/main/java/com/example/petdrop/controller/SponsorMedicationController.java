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

import com.example.petdrop.model.SponsorMedication;
import com.example.petdrop.repository.SponsorMedicationRepository;

@RestController
public class SponsorMedicationController {

    @Autowired
    private SponsorMedicationRepository sponsorMedicationRepo;

    // save sponsor medication to db
    @PostMapping("/addsponsormedication")
    public SponsorMedication addSponsorMedication(@RequestBody SponsorMedication sponsorMedication) {
        return sponsorMedicationRepo.save(sponsorMedication);
    }

    // use PUT for updating whole sponsor medications
    // updatedSponsorSponsorMedication must have all fields, not just ones to be updated
    @PutMapping("/updatesponsormedication")
    public ResponseEntity<SponsorMedication> updateSponsorMedication(@RequestBody SponsorMedication updatedSponsorMedication) {
        SponsorMedication savedSponsorMedication = sponsorMedicationRepo.save(updatedSponsorMedication);
        return ResponseEntity.ok(savedSponsorMedication);
    }

    // update a sponsor medication's name
    @PatchMapping("/updatesponsormedication/name/{id}")
    public long updateSponsorMedicationName(@PathVariable String id, @RequestBody String name) {
        return sponsorMedicationRepo.updateSponsorMedicationName(id, name);
    }

    // update a sponsor medication's instructions
    @PatchMapping("/updatesponsormedication/instructions/{id}")
    public long updateSponsorMedicationInstructions(@PathVariable String id, @RequestBody String[] instructions) {
        return sponsorMedicationRepo.updateSponsorMedicationInstructions(id, instructions);
    }

    // update a sponsor medication's video link
    @PatchMapping("/updatesponsormedication/videolink/{id}")
    public long updateSponsorMedicationVideoLink(@PathVariable String id, @RequestBody String videoLink) {
        return sponsorMedicationRepo.updateSponsorMedicationVideoLink(id, videoLink);
    }

    // get all sponsor medications from db
    @GetMapping("/getallsponsormedications")
    public List<SponsorMedication> getAllSponsorMedications() {
        return sponsorMedicationRepo.findAll();
    }

    // get sponsor medication from db using its id
    @GetMapping("/getsponsormedicationbyid/{id}")
    public Optional<SponsorMedication> getSponsorMedicationById(@PathVariable String id) {
        return sponsorMedicationRepo.findById(id);
    }

    // delete sponsor medication from db using its id
    @DeleteMapping("/deletesponsormedicationbyid/{id}")
    public void deleteSponsorMedicationById(@PathVariable String id) {
        sponsorMedicationRepo.deleteById(id);
    }

    // get sponsor medication from db using its name
    @GetMapping("/getsponsormedicationbyid/{name}")
    public Optional<SponsorMedication> getSponsorMedicationByName(@PathVariable String name) {
        return sponsorMedicationRepo.findByName(name);
    }
}
