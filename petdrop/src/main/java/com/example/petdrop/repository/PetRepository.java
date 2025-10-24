package com.example.petdrop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.Pet;

public interface PetRepository extends MongoRepository<Pet, String> {
	
	@Query("{medications.id:'?0'}")
	Optional<Pet> findPetByMedicationId(String medicationId);
}