package dev.mmiv.clinic.dto;

import java.time.LocalDate;

public record MedicalVisitResponse(
        int id,
        LocalDate visitDate,
        String visitType,
        String chiefComplaint,
        Double temperature,
        String bloodPressure,
        Integer pulseRate,
        Integer respiratoryRate,
        Double spo2,
        String history,
        String symptoms,
        String physicalExamFindings,
        String diagnosis,
        String plan,
        String treatment,
        String hama,
        String referralForm,
        String medicalChartImage,
        String fullName,
        LocalDate birthDate
) {}
