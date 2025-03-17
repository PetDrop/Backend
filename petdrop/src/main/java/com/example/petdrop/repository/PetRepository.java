package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.Medication;
import com.example.petdrop.model.Pet;

public interface PetRepository extends MongoRepository<Pet, String> {
	
	@Query("{id:'?0'}")
	@Update("{$set: {name:'?1'}}")
	long updatePetName(String id, String name);

	@Query("{id:'?0'}")
	@Update("{$set: {image:'?1'}}")
	long updatePetImage(String id, String image);

	@Query("{id:'?0'}")
	@Update("{$set: {age:'?1'}}")
	long updatePetAge(String id, int age);

	@Query("{id:'?0'}")
	@Update("{$set: {breed:'?1'}}")
	long updatePetBreed(String id, String breed);

	@Query("{id:'?0'}")
	@Update("{$set: {address:'?1'}}")
	long updatePetAddress(String id, String address);

	@Query("{id:'?0'}")
	@Update("{$set: {vet:'?1'}}")
	long updatePetVet(String id, String vet);

	@Query("{id:'?0'}")
	@Update("{$set: {vetPhone:'?1'}}")
	long updatePetVetPhone(String id, String vetPhone);

	@Query("{id:'?0'}")
	@Update("{$set: {medications:'?1'}}")
	long updatePetMedications(String id, Medication[] medications);
}