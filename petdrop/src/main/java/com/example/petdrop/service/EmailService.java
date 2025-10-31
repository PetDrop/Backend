package com.example.petdrop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.from:}")
	private String fromAddress;

	public void sendResetCode(String toEmail, String code) {
		// Fallback: if mail host isn't configured, log and return
		if (mailSender instanceof JavaMailSenderImpl jms) {
			if (jms.getHost() == null || jms.getHost().isBlank()) {
				System.out.println("[WARN] Mail host not configured. Would send reset code to " + toEmail + ": " + code);
				return;
			}
		}
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			if (fromAddress != null && !fromAddress.isBlank()) {
				message.setFrom(fromAddress);
			}
			message.setTo(toEmail);
			message.setSubject("Your PetDrop password reset code");
			message.setText("Use this 6-digit code to reset your password: " + code + "\n\nThis code expires in 10 minutes. If you didn't request this, you can ignore this email.");
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println("[ERROR] Failed to send reset code email: " + e.getMessage());
		}
	}
}


