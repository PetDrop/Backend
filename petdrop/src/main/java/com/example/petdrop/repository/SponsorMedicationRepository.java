package com.example.petdrop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.SponsorMedication;

public interface SponsorMedicationRepository extends MongoRepository<SponsorMedication, String> {
	
	@Query("{name:'?0'}")
	Optional<SponsorMedication> findByName(String name);

	@Query("{id:'?0'}")
	@Update("{$set: {name:'?1'}}")
	long updateSponsorMedicationName(String id, String name);
	
	@Query("{id:'?0'}")
	@Update("{$set: {instructions:'?1'}}")
	long updateSponsorMedicationInstructions(String id, String[] instructions);
	
	@Query("{id:'?0'}")
	@Update("{$set: {videoLink:'?1'}}")
	long updateSponsorMedicationVideoLink(String id, String videoLink);

}