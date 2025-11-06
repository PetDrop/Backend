package com.example.petdrop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.SponsorMedication;

public interface SponsorMedicationRepository extends MongoRepository<SponsorMedication, String> {
	
	@Query("{name:'?0'}")
	Optional<SponsorMedication> findByName(String name);

}