package com.example.petdrop.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("password_reset_code")
public class PasswordResetCode {

	@Id
	private String id;

	private String email;
	private String codeHash;
	private Instant createdAt;
	private Instant expiresAt;
	private Integer attempts;
	private Integer maxAttempts;
	private Instant usedAt;

	public PasswordResetCode() {
	}

	public PasswordResetCode(String email, String codeHash, Instant createdAt, Instant expiresAt, Integer maxAttempts) {
		this.email = email;
		this.codeHash = codeHash;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.attempts = 0;
		this.maxAttempts = maxAttempts;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getCodeHash() {
		return codeHash;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public Integer getMaxAttempts() {
		return maxAttempts;
	}

	public Instant getUsedAt() {
		return usedAt;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCodeHash(String codeHash) {
		this.codeHash = codeHash;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public void setUsedAt(Instant usedAt) {
		this.usedAt = usedAt;
	}
}
