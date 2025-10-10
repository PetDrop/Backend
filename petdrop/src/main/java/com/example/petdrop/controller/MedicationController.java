package com.example.petdrop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.DatabaseNotification;
import com.example.petdrop.model.Medication;
import com.example.petdrop.model.Notification;
import com.example.petdrop.model.NotificationRequest;
import com.example.petdrop.repository.MedicationRepository;
import com.example.petdrop.repository.NotificationRepository;

@RestController
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepo;

    @Autowired
    private NotificationRepository notificationRepo;

    // save medication to db
    @PostMapping("/add-medication")
    public Medication addMedication(@RequestBody Medication medication) {
        notificationRepo.saveAll(Notification.makeIntoDBNotifsList(medication.getNotifications()));
        medication.setNotifications(Notification.makeIntoDBNotifsArr(medication.getNotifications()));
        return medicationRepo.save(medication);
    }

    // adds notifs to med that doesn't already have any
    @PutMapping("/create-notifications-for-medication/{id}")
    public Medication createNotifications(@PathVariable String id,
            @RequestBody NotificationRequest[] notificationRequests) {
        Medication medication = medicationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));

        List<DatabaseNotification> notifs = Notification.makeIntoDBNotifsList(notificationRequests);
        notifs = notificationRepo.saveAll(notifs);

        medication.setNotifications(notifs.toArray(new DatabaseNotification[0]));
        return medicationRepo.save(medication);
    }

    // reconcile old notifs with new notifs for a med
    @PutMapping("/edit-notifications-for-medication/{id}")
    public Medication editNotifications(@PathVariable String id,
            @RequestBody NotificationRequest[] notificationRequests) {
        Medication medication = medicationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));
        List<DatabaseNotification> oldNotifs = Notification.makeIntoDBNotifsList(medication.getNotifications());

        List<DatabaseNotification> toDelete = new ArrayList<DatabaseNotification>();

        List<DatabaseNotification> notifs = Notification.makeIntoDBNotifsList(notificationRequests);
        notifs = notificationRepo.saveAll(notifs);

        Set<String> newNotifsIDs = notifs.stream()
                .map(DatabaseNotification::getId)
                .collect(Collectors.toSet());
        for (DatabaseNotification notif : oldNotifs) {
            if (!newNotifsIDs.contains(notif.getId())) {
                toDelete.add(notif);
            }
        }
        notificationRepo.deleteAll(toDelete);

        medication.setNotifications(notifs.toArray(new DatabaseNotification[0]));
        return medicationRepo.save(medication);
    }

    // deletes all notifs from med
    @PutMapping("/delete-notifications-from-medication/{id}")
    public Medication deleteNotifications(@PathVariable String id) {
        Medication medication = medicationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));

        notificationRepo.deleteAll(Notification.makeIntoDBNotifsList(medication.getNotifications()));

        medication.setNotifications(null);
        return medicationRepo.save(medication);
    }

    // updates all fields in med except notifs
    @PutMapping("/update-medication-not-notifications")
    public Medication updateMedication(@RequestBody Medication updatedMedication) {
        return medicationRepo.save(updatedMedication);
    }

    // updates all fields in med and creates notifs
    @PutMapping("/update-medication-create-notifications")
    public Medication updateMedicationCreateNotifications(@RequestBody Medication updatedMedication) {
        notificationRepo.saveAll(Notification.makeIntoDBNotifsList(updatedMedication.getNotifications()));
        return medicationRepo.save(updatedMedication);
    }

    // updates all fields in med including notifs
    @PutMapping("/update-medication-and-notifications")
    public Medication updateMedicationAndNotifications(@RequestBody Medication updatedMedication) {
        Medication oldMedication = medicationRepo.findById(updatedMedication.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));
        List<DatabaseNotification> oldNotifs = Notification.makeIntoDBNotifsList(oldMedication.getNotifications());

        List<DatabaseNotification> toDelete = new ArrayList<DatabaseNotification>();

        List<DatabaseNotification> notifs = Notification.makeIntoDBNotifsList(updatedMedication.getNotifications());
        notifs = notificationRepo.saveAll(notifs);

        Set<String> newNotifsIDs = notifs.stream()
                .map(DatabaseNotification::getId)
                .collect(Collectors.toSet());
        for (DatabaseNotification notif : oldNotifs) {
            if (!newNotifsIDs.contains(notif.getId())) {
                toDelete.add(notif);
            }
        }
        notificationRepo.deleteAll(toDelete);

        return medicationRepo.save(updatedMedication);
    }

    // updates all fields in med and deletes all its notifs
    @PutMapping("/update-medication-delete-notifications")
    public Medication updateMedicationDeleteNotifications(@RequestBody Medication updatedMedication) {
        Medication oldMedication = medicationRepo.findById(updatedMedication.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));

        List<DatabaseNotification> notifs = Notification.makeIntoDBNotifsList(oldMedication.getNotifications());
        notificationRepo.deleteAll(notifs);

        return medicationRepo.save(updatedMedication);
    }

    // delete medication from db using its id
    @DeleteMapping("/delete-medication/{id}")
    public void deleteMedicationById(@PathVariable String id) {
        Medication medication = medicationRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medication not found"));

        List<DatabaseNotification> notifs = Notification.makeIntoDBNotifsList(medication.getNotifications());
        notificationRepo.deleteAll(notifs);

        medicationRepo.delete(medication);
    }

    // get medication from db using its id
    @GetMapping("/get-medication/{id}")
    public Optional<Medication> getMedicationById(@PathVariable String id) {
        return medicationRepo.findById(id);
    }

    // get all medications from db
    @GetMapping("/get-all-medications")
    public List<Medication> getAllMedications() {
        return medicationRepo.findAll();
    }
}
