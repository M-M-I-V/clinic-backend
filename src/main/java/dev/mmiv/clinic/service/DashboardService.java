package dev.mmiv.clinic.service;

import dev.mmiv.clinic.repository.VisitsRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VisitsRepository visitsRepository;

    public Map<String, Long> getKpis() {
        return Map.of(
            "todayVisits", visitsRepository.countTodayVisits(),
            "monthVisits", visitsRepository.countMonthVisits()
        );
    }

    public List<Map<String, Object>> getTopDiagnoses() {
        return visitsRepository.countTopDiagnosesThisMonth().stream()
                .map(r -> Map.of("diagnosis", r[0], "count", r[1]))
                .toList();
    }

    public List<Map<String, Object>> getVisitsTrend() {
        return visitsRepository.countVisitsTrendLast30Days().stream()
                .map(r -> Map.of("date", r[0], "count", r[1]))
                .toList();
    }
}