package dev.mmiv.clinic.dto;

import java.time.LocalDate;

public record VisitsList(
        int id,
        LocalDate visitDate,
        String visitType,
        String chiefComplaint,
        String diagnosis
) {}
