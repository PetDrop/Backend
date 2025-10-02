package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.petdrop.model.Medication;

public interface MedicationRepository extends MongoRepository<Medication, String> {
	
}