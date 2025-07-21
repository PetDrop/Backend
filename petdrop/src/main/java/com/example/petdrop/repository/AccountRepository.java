package com.example.petdrop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.petdrop.model.Account;
import com.example.petdrop.model.Pet;

public interface AccountRepository extends MongoRepository<Account, String> {
	
	@Query("{email:'?0'}")
	Optional<Account> findAccountByEmail(String email);
	
	@Query("{username:'?0'}")
	Optional<Account> findAccountByUsername(String username);
	
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
	@Update("{$set: {usersSharedWith:'?1'}}")
	long updateAccountUsersSharedWith(String id, String[] usersSharedWith);

	@Query("{id:'?0'}")
	@Update("{$set: {pets:'?1'}}")
	long updateAccountPets(String id, Pet[] pets);

	@Query("{id:'?0'}")
	@Update("{$set: {image:'?1'}}")
	long updateAccountImage(String id, String image);
}