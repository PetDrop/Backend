package com.example.petdrop.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.petdrop.model.Account;
import com.example.petdrop.model.PasswordResetCode;
import com.example.petdrop.repository.AccountRepository;
import com.example.petdrop.repository.PasswordResetCodeRepository;

@Service
public class PasswordResetService {

	private static final Duration CODE_TTL = Duration.ofMinutes(10);
	private static final Duration REQUEST_THROTTLE = Duration.ofMinutes(1);
	private static final int MAX_ATTEMPTS = 5;

	@Autowired
	private PasswordResetCodeRepository resetCodeRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private EmailService emailService;

	public void requestReset(String email) {
		Optional<Account> accountOpt = accountRepository.findAccountByEmail(email);
		// Always behave as if request is accepted
		if (!accountOpt.isPresent()) {
			return;
		}

		Instant now = Instant.now();
		Optional<PasswordResetCode> latestOpt = resetCodeRepository.findFirstByEmailOrderByCreatedAtDesc(email);
		if (latestOpt.isPresent()) {
			PasswordResetCode latest = latestOpt.get();
			if (latest.getCreatedAt() != null && latest.getCreatedAt().isAfter(now.minus(REQUEST_THROTTLE)) && latest.getUsedAt() == null) {
				// Throttle: do not create a new code if requested too soon
				return;
			}
		}

		String code = generateSixDigitCode();
		String hash = sha256(code);
		PasswordResetCode prc = new PasswordResetCode(email, hash, now, now.plus(CODE_TTL), MAX_ATTEMPTS);
		resetCodeRepository.save(prc);

		// Send code via email (falls back to log if SMTP not configured)
		emailService.sendResetCode(email, code);
	}

	public boolean resetPassword(String email, String code, String newPassword) {
		Optional<Account> accountOpt = accountRepository.findAccountByEmail(email);
		if (!accountOpt.isPresent()) {
			// Treat as invalid without leaking existence
			return false;
		}

		Optional<PasswordResetCode> latestOpt = resetCodeRepository.findFirstByEmailOrderByCreatedAtDesc(email);
		if (!latestOpt.isPresent()) {
			return false;
		}
		PasswordResetCode latest = latestOpt.get();
		Instant now = Instant.now();
		if (latest.getUsedAt() != null) return false;
		if (latest.getExpiresAt() != null && now.isAfter(latest.getExpiresAt())) return false;
		if (latest.getAttempts() != null && latest.getMaxAttempts() != null && latest.getAttempts() >= latest.getMaxAttempts()) return false;

		String providedHash = sha256(code);
		if (!providedHash.equals(latest.getCodeHash())) {
			latest.setAttempts((latest.getAttempts() == null ? 0 : latest.getAttempts()) + 1);
			resetCodeRepository.save(latest);
			return false;
		}

		// Valid code
		latest.setUsedAt(now);
		resetCodeRepository.save(latest);

		Account account = accountOpt.get();
		account.setPassword(newPassword);
		accountRepository.save(account);
		return true;
	}

	private static String generateSixDigitCode() {
		int n = ThreadLocalRandom.current().nextInt(0, 1_000_000);
		return String.format("%06d", n);
	}

	private static String sha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 not available", e);
		}
	}
}


