package com.example.petdrop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.Reminder;
import com.example.petdrop.repository.ReminderRepository;

@RestController
public class ReminderController {
    
    @Autowired 
    private ReminderRepository reminderRepo;
    
    // save reminder to db
    @PostMapping("/addreminder")
    public Reminder addReminder(@RequestBody Reminder reminder) {
        return reminderRepo.save(reminder);
    }

    // use PUT for updating whole reminders
    // updatedReminder must have all fields, not just ones to be updated
    @PutMapping("/updatereminder")
    public ResponseEntity<Reminder> updateReminder(@RequestBody Reminder updatedReminder) {
        Reminder savedReminder = reminderRepo.save(updatedReminder);
        return ResponseEntity.ok(savedReminder);
    }

    // update a reminder's notifications
    @PatchMapping("/updatereminder/notifications/{id}")
    public long updateReminderNotifications(@PathVariable String id, @RequestBody String[] notifications) {
        return reminderRepo.updateReminderNotifications(id, notifications);
    }

    // get all reminders from db
    @GetMapping("/getallreminders")
    public List<Reminder> getAllReminders() {
        return reminderRepo.findAll(); 
    }

    // get reminder from db using its id
    @GetMapping("/getreminderbyid/{id}")
    public Optional<Reminder> getReminderById(@PathVariable String id) {
        return reminderRepo.findById(id);
    }
    
}
