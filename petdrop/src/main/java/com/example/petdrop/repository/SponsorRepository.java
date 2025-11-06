package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.Sponsor;

public interface SponsorRepository extends MongoRepository<Sponsor, String> {
    
    @Query("{name:'?0'}")
    Optional<Sponsor> findByName(String name);

}
