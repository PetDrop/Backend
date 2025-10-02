package com.example.petdrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petdrop.model.DatabaseNotification;
import com.example.petdrop.model.NotificationRequest;
import com.example.petdrop.repository.NotificationRepository;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    @PostMapping("/add-notification")
    public DatabaseNotification addNotification(@RequestBody NotificationRequest notificationRequest) {
        return repo.save(NotificationRequest.makeIntoDBNotif(notificationRequest));
    }

    @DeleteMapping("/delete-notification/{id}")
    public void deleteNotification(@PathVariable String id) {
        repo.findById(id).ifPresent(n -> {
            repo.delete(n);
        });
    }
}
