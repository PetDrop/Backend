package com.example.petdrop.controller;

import java.util.List;
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

import com.example.petdrop.model.Notification;
import com.example.petdrop.model.Medication;
import com.example.petdrop.repository.MedicationRepository;
import com.example.petdrop.repository.NotificationRepository;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notifRepo;

    @Autowired
    private MedicationRepository medRepo;

    @PostMapping("/add-notification/{id}")
    public Notification addNotification(@PathVariable String id, @RequestBody Notification notification) {
        Medication med = medRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));

        med.getNotifications().add(notification);
        medRepo.save(med);
        return notifRepo.save(notification);
    }

    @PutMapping("update-notification")
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
}
