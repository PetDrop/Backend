package com.example.petdrop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.petdrop.model.Pet;
import com.example.petdrop.repository.PetRepository;
import com.example.petdrop.service.EmailService;

@RestController
public class IOPController {

    @Autowired
    private PetRepository petRepo;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-iop-measurement")
    public ResponseEntity<?> sendIOPMeasurement(@RequestBody IOPRequest request) {
        // Validate request
        if (request == null || request.petId == null || request.petId.isBlank() ||
            request.iopMeasurement == null || request.iopMeasurement.isBlank() ||
            request.ownerEmail == null || request.ownerEmail.isBlank() ||
            request.ownerName == null || request.ownerName.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing required fields: petId, iopMeasurement, ownerEmail, ownerName");
        }

        // Fetch pet by ID
        Optional<Pet> petOpt = petRepo.findById(request.petId);
        if (petOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Pet not found with id: " + request.petId);
        }

        Pet pet = petOpt.get();

        // Validate vet email exists
        if (pet.getVet() == null || pet.getVet().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Pet does not have a veterinarian email address");
        }

        // Send email
        try {
            emailService.sendIOPMeasurement(
                pet.getVet(),
                request.ownerEmail,
                request.ownerName,
                pet.getName(),
                pet.getAge(),
                pet.getBreed(),
                request.iopMeasurement
            );
            return ResponseEntity.ok().body("IOP measurement email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/send-iop-measurement-report")
    public ResponseEntity<?> sendIOPMeasurementReport(@RequestBody IOPReportRequest request) {
        // Validate request
        if (request == null || request.petId == null || request.petId.isBlank() ||
            request.ownerEmail == null || request.ownerEmail.isBlank() ||
            request.ownerName == null || request.ownerName.isBlank() ||
            request.graphImageBase64 == null || request.graphImageBase64.isBlank() ||
            request.measurements == null || request.measurements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing required fields: petId, ownerEmail, ownerName, graphImageBase64, measurements");
        }

        // Fetch pet by ID
        Optional<Pet> petOpt = petRepo.findById(request.petId);
        if (petOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Pet not found with id: " + request.petId);
        }

        Pet pet = petOpt.get();

        // Validate vet email exists
        if (pet.getVet() == null || pet.getVet().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Pet does not have a veterinarian email address");
        }

        // Convert measurements to EmailService format
        List<EmailService.MeasurementData> measurementDataList = request.measurements.stream()
            .map(m -> {
                EmailService.MeasurementData data = new EmailService.MeasurementData();
                data.value = m.value;
                data.timestamp = m.timestamp;
                return data;
            })
            .toList();

        // Send email
        try {
            emailService.sendIOPMeasurementReport(
                pet.getVet(),
                request.ownerEmail,
                request.ownerName,
                pet.getName(),
                pet.getAge(),
                pet.getBreed(),
                request.graphImageBase64,
                measurementDataList
            );
            return ResponseEntity.ok().body("IOP measurement report sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send email: " + e.getMessage());
        }
    }

    public static class IOPRequest {
        public String petId;
        public String iopMeasurement;
        public String ownerEmail;
        public String ownerName;
    }

    public static class IOPReportRequest {
        public String petId;
        public String ownerEmail;
        public String ownerName;
        public String graphImageBase64;
        public List<MeasurementData> measurements;
    }

    public static class MeasurementData {
        public String value;
        public String timestamp;
    }
}

