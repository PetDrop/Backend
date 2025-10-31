package com.example.petdrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petdrop.service.PasswordResetService;

@RestController
public class AuthController {

	@Autowired
	private PasswordResetService passwordResetService;

	@PostMapping("/api/auth/password/forgot")
	public ResponseEntity<Void> forgot(@RequestBody EmailRequest req) {
		if (req == null || req.email == null) {
			return ResponseEntity.ok().build();
		}
		passwordResetService.requestReset(req.email);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/auth/password/reset")
	public ResponseEntity<Void> reset(@RequestBody ResetRequest req) {
		if (req == null || req.email == null || req.code == null || req.newPassword == null) {
			return ResponseEntity.badRequest().build();
		}
		boolean ok = passwordResetService.resetPassword(req.email, req.code, req.newPassword);
		return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
	}

	public static class EmailRequest { public String email; }
	public static class ResetRequest { public String email; public String code; public String newPassword; }
}


