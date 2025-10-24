package dev.mmiv.clinic.dto;

import java.time.LocalDate;

public record DentalVisitResponse(
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
        String physicalExamFindings,
        String diagnosis,
        String plan,
        String treatment,
        String dentalChartImage,
        String diagnosticTestResult,
        String diagnosticTestImage,
        String patientName,
        LocalDate birthDate
) {}
