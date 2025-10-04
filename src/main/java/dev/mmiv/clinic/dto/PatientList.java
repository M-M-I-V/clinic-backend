package dev.mmiv.clinic.dto;

public record PatientList(
        int id,
        String fullName,
        String studentId,
        String gender,
        String program,
        String knownDiseases
) {}