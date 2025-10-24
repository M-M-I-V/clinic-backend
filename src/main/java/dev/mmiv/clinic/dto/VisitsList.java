package dev.mmiv.clinic.dto;

import java.time.LocalDate;

public record VisitsList(
        int id,
        String fullName,
        LocalDate birthDate,
        LocalDate visitDate,
        String visitType,
        String chiefComplaint,
        String physicalExamFindings,
        String diagnosis,
        String treatment
) {}