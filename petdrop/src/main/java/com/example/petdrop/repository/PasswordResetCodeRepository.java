package com.example.petdrop.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.petdrop.model.PasswordResetCode;

public interface PasswordResetCodeRepository extends MongoRepository<PasswordResetCode, String> {

	Optional<PasswordResetCode> findFirstByEmailOrderByCreatedAtDesc(String email);
}


