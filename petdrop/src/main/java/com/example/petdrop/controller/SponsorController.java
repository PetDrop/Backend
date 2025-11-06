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

import com.example.petdrop.model.Sponsor;
import com.example.petdrop.repository.SponsorRepository;

@RestController
public class SponsorController {

    @Autowired
    private SponsorRepository sponsorRepo;

    // save sponsor to db
    @PostMapping("/add-sponsor")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return sponsorRepo.save(sponsor);
    }

    // update all fields of sponsor
    @PutMapping("/update-sponsor")
        public ResponseEntity<Sponsor> updateSponsor(@RequestBody Sponsor updatedSponsor) {
        Sponsor savedSponsor = sponsorRepo.save(updatedSponsor);
        return ResponseEntity.ok(savedSponsor);
    }

    // get all sponsors from db
    @GetMapping("/get-all-sponsors")
    public List<Sponsor> getAllSponsors() {
        return sponsorRepo.findAll();
    }

    // get sponsor from db using its id
    @GetMapping("/get-sponsor-by-id/{id}")
    public Optional<Sponsor> getSponsorById(@PathVariable String id) {
        return sponsorRepo.findById(id);
    }

    // get sponsor from db using its name
    @GetMapping("/get-sponsor-by-name/{name}")
    public Optional<Sponsor> getSponsorByName(@PathVariable String name) {
        return sponsorRepo.findByName(name);
    }

    // delete sponsor from db using its id
    @DeleteMapping("/delete-sponsor/{id}")
    public void deleteSponsorById(@PathVariable String id) {
        sponsorRepo.deleteById(id);
    }
}
