package com.example.petdrop.dto;

public record MedicationAdministeredRequest(
    String ownerUsername,
    String medName,
    String petName,
    String administeredByUsername
) {}
