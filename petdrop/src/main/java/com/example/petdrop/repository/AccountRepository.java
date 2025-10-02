package com.example.petdrop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
	
	@Query("{email:'?0'}")
	Optional<Account> findAccountByEmail(String email);
	
	@Query("{username:'?0'}")
	Optional<Account> findAccountByUsername(String username);
}