package com.example.petdrop.service;

import java.time.LocalDateTime;

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

	public void sendIOPMeasurement(String vetEmail, String ownerEmail, String ownerName, String petName, int petAge, String petBreed, String iopMeasurement) {
		// Fallback: if mail host isn't configured, log and return
		if (mailSender instanceof JavaMailSenderImpl jms) {
			if (jms.getHost() == null || jms.getHost().isBlank()) {
				System.out.println("[WARN] Mail host not configured. Would send IOP measurement to " + vetEmail + " (CC: " + ownerEmail + ")");
				return;
			}
		}
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			if (fromAddress != null && !fromAddress.isBlank()) {
				message.setFrom(fromAddress);
			}
			message.setTo(vetEmail);
			message.setCc(ownerEmail);
			message.setSubject("IOP Measurement Report from " + ownerName);
			
			// Format email body with pet details and IOP measurement
			StringBuilder emailBody = new StringBuilder();
			emailBody.append("Intra-Ocular Pressure Measurement Report\n\n");
			emailBody.append("Pet Information:\n");
			emailBody.append("  Name: ").append(petName).append("\n");
			emailBody.append("  Age: ").append(petAge).append(" years\n");
			emailBody.append("  Breed: ").append(petBreed).append("\n\n");
			emailBody.append("IOP Measurement: ").append(iopMeasurement).append(" mmHg\n\n");
			emailBody.append("Reported by: ").append(ownerName).append("\n");
			emailBody.append("Date: ").append(LocalDateTime.now().toString()).append("\n");
			
			message.setText(emailBody.toString());
			mailSender.send(message);
		} catch (org.springframework.mail.MailAuthenticationException e) {
			System.out.println("[ERROR] Failed to send IOP measurement email - Authentication failed");
			System.out.println("[ERROR] Check SMTP username and password configuration");
			System.out.println("[ERROR] Exception details: " + e.getMessage());
			e.printStackTrace();
		} catch (org.springframework.mail.MailSendException e) {
			System.out.println("[ERROR] Failed to send IOP measurement email - Send failed");
			System.out.println("[ERROR] Exception details: " + e.getMessage());
			if (e.getFailedMessages() != null && !e.getFailedMessages().isEmpty()) {
				System.out.println("[ERROR] Failed recipients: " + e.getFailedMessages().keySet());
			}
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("[ERROR] Failed to send IOP measurement email: " + e.getMessage());
			System.out.println("[ERROR] Exception type: " + e.getClass().getName());
			e.printStackTrace();
		}
	}
}


