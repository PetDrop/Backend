package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.Reminder;

public interface ReminderRepository extends MongoRepository<Reminder, String> {
	
	@Query("{id:'?0'}")
	@Update("{$set: {notifications:'?1'}}")
	long updateReminderNotifications(String id, String[] notifications);
}