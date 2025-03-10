package com.example.petdrop.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
	
	@Query("{email:'?0'}")
	Account findItemByEmail(String email);
	
	public long count();
}