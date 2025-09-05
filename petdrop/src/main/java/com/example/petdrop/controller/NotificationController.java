package com.example.petdrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petdrop.model.Notification;
import com.example.petdrop.repository.NotificationRepository;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    @PostMapping("/schedulenotification")
    public Notification create(@RequestBody Notification notification) {
        return repo.save(notification);
    }

    @DeleteMapping("/cancelnotification/{id}")
    public void cancel(@PathVariable String id) {
        repo.findById(id).ifPresent(n -> {
            repo.delete(n);
        });
    }
}

