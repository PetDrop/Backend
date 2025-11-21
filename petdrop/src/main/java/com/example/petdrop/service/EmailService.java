package com.example.petdrop.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

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

	public void sendIOPMeasurementReport(String vetEmail, String ownerEmail, String ownerName, 
			String petName, int petAge, String petBreed, String graphImageBase64, 
			List<MeasurementData> measurements) {
		// Fallback: if mail host isn't configured, log and return
		if (mailSender instanceof JavaMailSenderImpl jms) {
			if (jms.getHost() == null || jms.getHost().isBlank()) {
				System.out.println("[WARN] Mail host not configured. Would send IOP measurement report to " + vetEmail + " (CC: " + ownerEmail + ")");
				return;
			}
		}
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			if (fromAddress != null && !fromAddress.isBlank()) {
				helper.setFrom(fromAddress);
			}
			helper.setTo(vetEmail);
			helper.setCc(ownerEmail);
			helper.setSubject("IOP Measurement Report from " + ownerName);
			
			// Build HTML email body with data table
			StringBuilder htmlBody = new StringBuilder();
			htmlBody.append("<!DOCTYPE html><html><head><style>");
			htmlBody.append("body { font-family: Arial, sans-serif; }");
			htmlBody.append("table { border-collapse: collapse; width: 100%; margin: 20px 0; }");
			htmlBody.append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
			htmlBody.append("th { background-color: #6495ED; color: white; }");
			htmlBody.append("</style></head><body>");
			htmlBody.append("<h2>Intra-Ocular Pressure Measurement Report</h2>");
			htmlBody.append("<h3>Pet Information:</h3>");
			htmlBody.append("<p><strong>Name:</strong> ").append(petName).append("</p>");
			htmlBody.append("<p><strong>Age:</strong> ").append(petAge).append(" years</p>");
			htmlBody.append("<p><strong>Breed:</strong> ").append(petBreed).append("</p>");
			
			htmlBody.append("<h3>Measurements:</h3>");
			htmlBody.append("<table>");
			htmlBody.append("<tr><th>Date/Time</th><th>IOP Value (mmHg)</th></tr>");
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			for (MeasurementData measurement : measurements) {
				htmlBody.append("<tr>");
				htmlBody.append("<td>").append(measurement.timestamp).append("</td>");
				htmlBody.append("<td>").append(measurement.value).append("</td>");
				htmlBody.append("</tr>");
			}
			
			htmlBody.append("</table>");
			htmlBody.append("<p><strong>Reported by:</strong> ").append(ownerName).append("</p>");
			htmlBody.append("<p><strong>Report Date:</strong> ").append(LocalDateTime.now().format(formatter)).append("</p>");
			
			// Attach graph image if available
			if (graphImageBase64 != null && !graphImageBase64.isBlank()) {
				htmlBody.append("<p>See attached graph image for visual representation.</p>");
				byte[] imageBytes = Base64.getDecoder().decode(graphImageBase64);
				helper.addAttachment("iop_measurement_graph.png", 
					() -> new java.io.ByteArrayInputStream(imageBytes), 
					"image/png");
			} else {
				htmlBody.append("<p><em>Note: Graph image could not be generated. Please refer to the data table above.</em></p>");
			}
			
			htmlBody.append("</body></html>");
			
			helper.setText(htmlBody.toString(), true);
			
			mailSender.send(message);
		} catch (org.springframework.mail.MailAuthenticationException e) {
			System.out.println("[ERROR] Failed to send IOP measurement report email - Authentication failed");
			System.out.println("[ERROR] Check SMTP username and password configuration");
			System.out.println("[ERROR] Exception details: " + e.getMessage());
			e.printStackTrace();
		} catch (org.springframework.mail.MailSendException e) {
			System.out.println("[ERROR] Failed to send IOP measurement report email - Send failed");
			System.out.println("[ERROR] Exception details: " + e.getMessage());
			if (e.getFailedMessages() != null && !e.getFailedMessages().isEmpty()) {
				System.out.println("[ERROR] Failed recipients: " + e.getFailedMessages().keySet());
			}
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("[ERROR] Failed to send IOP measurement report email: " + e.getMessage());
			System.out.println("[ERROR] Exception type: " + e.getClass().getName());
			e.printStackTrace();
		}
	}

	public static class MeasurementData {
		public String value;
		public String timestamp;
	}
}


