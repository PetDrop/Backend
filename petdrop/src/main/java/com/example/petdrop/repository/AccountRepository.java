package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.Account;
import com.example.petdrop.model.Pet;
import com.example.petdrop.model.Reminder;

public interface AccountRepository extends MongoRepository<Account, String> {
	
	@Query("{email:'?0'}")
	Account findAccountByEmail(String email);
	
	@Query("{username:'?0'}")
	Account findAccountByUsername(String username);
	
	@Query("{id:'?0'}")
	@Update("{$set: {username:'?1'}}")
	long updateAccountUsername(String id, String username);

	@Query("{id:'?0'}")
	@Update("{$set: {email:'?1'}}")
	long updateAccountEmail(String id, String email);

	@Query("{id:'?0'}")
	@Update("{$set: {password:'?1'}}")
	long updateAccountPassword(String id, String password);

	@Query("{id:'?0'}")
	@Update("{$set: {sharedUsers:'?1'}}")
	long updateAccountSharedUsers(String id, String[] sharedUsers);

	@Query("{id:'?0'}")
	@Update("{$set: {pets:'?1'}}")
	long updateAccountPets(String id, Pet[] pets);

	@Query("{id:'?0'}")
	@Update("{$set: {reminders:'?1'}}")
	long updateAccountReminders(String id, Reminder[] reminders);
}