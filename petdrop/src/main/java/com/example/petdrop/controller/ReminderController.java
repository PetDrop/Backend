package com.example.petdrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petdrop.model.Reminder;
import com.example.petdrop.repository.ReminderRepository;

@RestController
public class ReminderController {

    @Autowired
    private ReminderRepository repo;

    @PostMapping("/addreminder")
    public Reminder addReminder(@RequestBody Reminder reminder) {
        return repo.save(reminder);
    }

    @DeleteMapping("/deletereminder/{id}")
    public void deleteReminder(@PathVariable String id) {
        repo.findById(id).ifPresent(n -> {
            repo.delete(n);
        });
    }
}

