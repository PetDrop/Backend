package com.example.petdrop.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.petdrop.dto.MedicationAdministeredRequest;
import com.example.petdrop.model.Account;
import com.example.petdrop.model.Notification;
import com.example.petdrop.model.Medication;
import com.example.petdrop.repository.AccountRepository;
import com.example.petdrop.repository.MedicationRepository;
import com.example.petdrop.repository.NotificationRepository;
import com.example.petdrop.service.ExpoPushService;
import com.example.petdrop.service.NotificationSharingService;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notifRepo;

    @Autowired
    private MedicationRepository medRepo;

    @Autowired
    private NotificationSharingService sharingService;

    @Autowired
    private ExpoPushService expoPushService;

    @Autowired
    private AccountRepository accountRepo;

    @PostMapping("/add-notification/{id}")
    public Notification addNotification(@PathVariable String id, @RequestBody Notification notification) {
        Medication med = medRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));

        med.getNotifications().add(notification);
        medRepo.save(med);
        return notifRepo.save(notification);
    }

    @PutMapping("/update-notification")
    public Notification updateNotification(@RequestBody Notification notification) {
        return notifRepo.save(notification);
    }

    @DeleteMapping("/delete-notification/{id}")
    public void deleteNotification(@PathVariable String id, @RequestParam String medId) {
        Medication med = medRepo.findById(medId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));
        Notification notifToDelete = notifRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));

        // Remove the notification from the medication's list by filtering
        List<Notification> updatedNotifications = med.getNotifications().stream()
                .filter(notif -> !notif.getId().equals(id))
                .collect(Collectors.toList());
        med.setNotifications(updatedNotifications);
        medRepo.save(med);
        
        // Delete the notification from the notification collection
        notifRepo.delete(notifToDelete);
    }

    @PostMapping("/notify-medication-administered")
    public void notifyMedicationAdministered(@RequestBody MedicationAdministeredRequest request) {
        if (request.ownerUsername() == null || request.ownerUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ownerUsername is required");
        }
        List<String> recipients = sharingService.findAllRecipients(request.ownerUsername());

        // Exclude the person who administered - they don't need to notify themselves
        String administeredBy = request.administeredByUsername();
        String tokenToExclude = null;
        if (administeredBy != null && !administeredBy.isBlank()) {
            Optional<Account> adminAccount = accountRepo.findAccountByUsername(administeredBy);
            if (adminAccount.isPresent()) {
                tokenToExclude = adminAccount.get().getExpoPushToken();
            }
        }
        if (tokenToExclude != null && !tokenToExclude.isEmpty()) {
            recipients.removeIf(tokenToExclude::equals);
        }

        if (recipients.isEmpty()) {
            return;
        }
        String medName = request.medName() != null ? request.medName() : "Medication";
        String petName = request.petName() != null ? request.petName() : "your pet";
        String administeredByDisplay = administeredBy != null ? administeredBy : "Someone";
        String notificationBody = medName + " was given to " + petName + " by " + administeredByDisplay;

        Notification notification = new Notification();
        notification.setTitle("Medication Administered");
        notification.setBody(notificationBody);
        notification.setData(Collections.emptyMap());

        expoPushService.sendPushToMultipleRecipients(notification, recipients);
    }
}
