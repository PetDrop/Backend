package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
	
	@Query("{email:'?0'}")
	Account findAccountByEmail(String email);
	
	@Query("{username:'?0'}")
	Account findAccountByUsername(String username);
	
	@Query("{phone:'?0'}")
	Account findAccountByPhone(String phone);

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
	@Update("{$set: {phone:'?1'}}")
	long updateAccountPhone(String id, String phone);

	@Query("{id:'?0'}")
	@Update("{$set: {address:'?1'}}")
	long updateAccountAddress(String id, String address);

	@Query("{id:'?0'}")
	@Update("{$set: {emergencyContacts:'?1'}}")
	long updateAccountEmergencyContacts(String id, String[] emergencyContacts);
}