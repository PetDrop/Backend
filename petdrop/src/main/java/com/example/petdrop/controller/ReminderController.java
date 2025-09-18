package com.example.petdrop.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petdrop.model.Reminder;
import com.example.petdrop.model.ReminderRequest;
import com.example.petdrop.repository.ReminderRepository;

@RestController
public class ReminderController {

    @Autowired
    private ReminderRepository repo;

    @PostMapping("/addreminder")
    public Reminder addReminder(@RequestBody ReminderRequest reminderRequest) {
        // store repeated fields to be concise and efficient
        String[] nextLocalRuns = reminderRequest.getNextLocalRuns();
        String[] finalLocalRuns = reminderRequest.getFinalLocalRuns();
        String zoneId = reminderRequest.getZoneId();

        // instantiate arrays to be populated
        ZonedDateTime[] nextRuns = new ZonedDateTime[nextLocalRuns.length];
        ZonedDateTime[] finalRuns = new ZonedDateTime[finalLocalRuns.length];

        // get ZonedDateTime from each LocalDateTime formatted string using zoneIds (arrays must be kept consistent to avoid errors here)
        for (int i = 0; i < nextRuns.length; i++) {
            nextRuns[i] = Instant.parse(nextLocalRuns[i]).atZone(ZoneId.of(zoneId));
            finalRuns[i] = Instant.parse(finalLocalRuns[i]).atZone(ZoneId.of(zoneId));
        }

        // create Reminder with populated values and save it to db
        return repo.save(new Reminder(reminderRequest, nextRuns, finalRuns));
    }

    @DeleteMapping("/deletereminder/{id}")
    public void deleteReminder(@PathVariable String id) {
        repo.findById(id).ifPresent(n -> {
            repo.delete(n);
        });
    }
}
