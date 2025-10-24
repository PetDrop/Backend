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

import com.example.petdrop.model.SponsorMedication;
import com.example.petdrop.repository.SponsorMedicationRepository;

@RestController
public class SponsorMedicationController {

    @Autowired
    private SponsorMedicationRepository sponsorMedicationRepo;

    // save sponsor medication to db
    @PostMapping("/add-sponsor-medication")
    public SponsorMedication addSponsorMedication(@RequestBody SponsorMedication sponsorMedication) {
        return sponsorMedicationRepo.save(sponsorMedication);
    }

    // update all fields of sponssor medication
    @PutMapping("/update-sponsor-medication")
    public ResponseEntity<SponsorMedication> updateSponsorMedication(@RequestBody SponsorMedication updatedSponsorMedication) {
        SponsorMedication savedSponsorMedication = sponsorMedicationRepo.save(updatedSponsorMedication);
        return ResponseEntity.ok(savedSponsorMedication);
    }

    // get all sponsor medications from db
    @GetMapping("/get-all-sponsor-medications")
    public List<SponsorMedication> getAllSponsorMedications() {
        return sponsorMedicationRepo.findAll();
    }

    // get sponsor medication from db using its id
    @GetMapping("/get-sponsor-medication-by-id/{id}")
    public Optional<SponsorMedication> getSponsorMedicationById(@PathVariable String id) {
        return sponsorMedicationRepo.findById(id);
    }

    // get sponsor medication from db using its name
    @GetMapping("/get-sponsor-medication-by-name/{name}")
    public Optional<SponsorMedication> getSponsorMedicationByName(@PathVariable String name) {
        return sponsorMedicationRepo.findByName(name);
    }

    // delete sponsor medication from db using its id
    @DeleteMapping("/delete-sponsor-medication/{id}")
    public void deleteSponsorMedicationById(@PathVariable String id) {
        sponsorMedicationRepo.deleteById(id);
    }
}
