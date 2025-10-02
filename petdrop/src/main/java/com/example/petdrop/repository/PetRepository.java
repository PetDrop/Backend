package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.petdrop.model.Pet;

public interface PetRepository extends MongoRepository<Pet, String> {
	
}