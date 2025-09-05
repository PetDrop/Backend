package com.example.petdrop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.petdrop.model.DateObj;
import com.example.petdrop.model.Medication;
import com.example.petdrop.model.Reminder;
import com.example.petdrop.repository.MedicationRepository;
import com.example.petdrop.repository.ReminderRepository;

@RestController
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepo;

    @Autowired
    private ReminderRepository reminderRepo;

    // save medication to db
    @PostMapping("/addmedication")
    public Medication addMedication(@RequestBody Medication medication) {
        return medicationRepo.save(medication);
    }

    // use PUT for updating whole medications
    // updatedMedication must have all fields, not just ones to be updated
    @PutMapping("/updatemedication")
    public ResponseEntity<Medication> updateMedication(@RequestBody Medication updatedMedication) {
        Medication savedMedication = medicationRepo.save(updatedMedication);
        return ResponseEntity.ok(savedMedication);
    }

    // update a medication's name
    @PatchMapping("/updatemedication/name/{id}")
    public long updateMedicationName(@PathVariable String id, @RequestBody String name) {
        return medicationRepo.updateMedicationName(id, name);
    }

    // update a medication's color
    @PatchMapping("/updatemedication/color/{id}")
    public long updateMedicationColor(@PathVariable String id, @RequestBody String color) {
        return medicationRepo.updateMedicationColor(id, color);
    }

    // update a medication's description
    @PatchMapping("/updatemedication/description/{id}")
    public long updateMedicationDescription(@PathVariable String id, @RequestBody String description) {
        return medicationRepo.updateMedicationDescription(id, description);
    }

    // update a medication's dates
    @PatchMapping("/updatemedication/dates/{id}")
    public long updateMedicationDates(@PathVariable String id, @RequestBody DateObj[] dates) {
        return medicationRepo.updateMedicationDates(id, dates);
    }

    // update a medication's reminders
    @PatchMapping("/updatemedication/reminders/{id}")
    public long updateMedicationReminders(@PathVariable String id, @RequestBody Reminder[] reminders) {
        return medicationRepo.updateMedicationReminders(id, reminders);
    }

    // update a medication's range
    @PatchMapping("/updatemedication/range/{id}")
    public long updateMedicationRange(@PathVariable String id, @RequestBody int range) {
        return medicationRepo.updateMedicationRange(id, range);
    }

    // get all medications from db
    @GetMapping("/getallmedications")
    public List<Medication> getAllMedications() {
        return medicationRepo.findAll();
    }

    // get medication from db using its id
    @GetMapping("/getmedicationbyid/{id}")
    public Optional<Medication> getMedicationById(@PathVariable String id) {
        return medicationRepo.findById(id);
    }

    // delete medication from db using its id
    @DeleteMapping("/deletemedicationbyid/{id}")
    public void deleteMedicationById(@PathVariable String id) {
        // delete the med's reminders
        Reminder[] reminders = getMedicationById(id).get().getReminders();
        if (reminders != null) {
            for (Reminder reminder : reminders) {
                reminderRepo.delete(reminder);
            }
        }
        medicationRepo.deleteById(id);
    }

}
