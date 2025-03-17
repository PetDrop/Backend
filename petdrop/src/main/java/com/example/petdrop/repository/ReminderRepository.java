package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.Medication;
import com.example.petdrop.model.Pet;
import com.example.petdrop.model.Reminder;

public interface ReminderRepository extends MongoRepository<Reminder, String> {
	
	@Query("{id:'?0'}")
	@Update("{$set: {medication:'?1'}}")
	long updateReminderMedication(String id, Medication medication);

	@Query("{id:'?0'}")
	@Update("{$set: {pet:'?1'}}")
	long updateReminderPet(String id, Pet pet);
}