package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.DateObj;
import com.example.petdrop.model.Medication;
import com.example.petdrop.model.Notification;

public interface MedicationRepository extends MongoRepository<Medication, String> {
	
	@Query("{id:'?0'}")
	@Update("{$set: {name:'?1'}}")
	long updateMedicationName(String id, String name);

	@Query("{id:'?0'}")
	@Update("{$set: {color:'?1'}}")
	long updateMedicationColor(String id, String color);

	@Query("{id:'?0'}")
	@Update("{$set: {description:'?1'}}")
	long updateMedicationDescription(String id, String description);

	@Query("{id:'?0'}")
	@Update("{$set: {dates:'?1'}}")
	long updateMedicationDates(String id, DateObj[] dates);

	@Query("{id:'?0'}")
	@Update("{$set: {notifications:'?1'}}")
	long updateMedicationNotifications(String id, Notification[] notifications);

	@Query("{id:'?0'}")
	@Update("{$set: {range:'?1'}}")
	long updateMedicationRange(String id, int range);
}