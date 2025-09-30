package dev.mmiv.clinic.dto;

import java.time.LocalDate;

public record VisitTrend(LocalDate date, Long count) {}