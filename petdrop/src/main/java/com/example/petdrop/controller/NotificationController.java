package com.example.petdrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.petdrop.model.DatabaseNotification;
import com.example.petdrop.model.Medication;
import com.example.petdrop.model.Notification;
import com.example.petdrop.model.NotificationRequest;
import com.example.petdrop.repository.MedicationRepository;
import com.example.petdrop.repository.NotificationRepository;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notifRepo;

    @Autowired
    private MedicationRepository medRepo;

    @PostMapping("/add-notification/{id}")
    public DatabaseNotification addNotification(@PathVariable String id,
            @RequestBody NotificationRequest notificationRequest) {
        Medication med = medRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));
        DatabaseNotification newNotif = notificationRequest.makeIntoDBNotif();

        DatabaseNotification[] newNotifs = new DatabaseNotification[med.getNotifications().length + 1];
        DatabaseNotification[] oldNotifs = Notification.makeIntoDBNotifsArr(med.getNotifications());
        for (int i = 0; i < oldNotifs.length; i++) {
            newNotifs[i] = oldNotifs[i];
        }
        newNotifs[newNotifs.length - 1] = newNotif;

        med.setNotifications(newNotifs);
        return notifRepo.save(newNotif);
    }

    @PutMapping("update-notification")
    public DatabaseNotification updateNotification(@RequestBody NotificationRequest notificationRequest) {
        return notifRepo.save(notificationRequest.makeIntoDBNotif());
    }

    @DeleteMapping("/delete-notification/{id}")
    public void deleteNotification(@PathVariable String notifId, @RequestBody String medId) {
        Medication med = medRepo.findById(medId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));
        DatabaseNotification notifToDelete = notifRepo.findById(notifId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found"));

        DatabaseNotification[] oldNotifs = Notification.makeIntoDBNotifsArr(med.getNotifications());
        DatabaseNotification[] newNotifs = new DatabaseNotification[oldNotifs.length - 1];
        int i, j = 0;
        for (i = 0; i < oldNotifs.length; i++) {
            if (!oldNotifs[i].getId().equals(notifId)) {
                newNotifs[j++] = oldNotifs[i];
            }
        }
        med.setNotifications(newNotifs);
        
        medRepo.save(med);
        notifRepo.delete(notifToDelete);
    }
}
