package dev.mmiv.clinic.service;

import dev.mmiv.clinic.repository.VisitsRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final VisitRepository visitRepository;

    public Map<String, Long> getKpis() {
        return Map.of(
            "todayVisits", visitRepository.countTodayVisits(),
            "monthVisits", visitRepository.countMonthVisits()
        );
    }

    public List<Map<String, Object>> getTopDiagnoses() {
        return visitRepository.countTopDiagnosesThisMonth().stream()
                .map(r -> Map.of("diagnosis", r[0], "count", r[1]))
                .toList();
    }

    public List<Map<String, Object>> getVisitsTrend() {
        return visitRepository.countVisitsTrendLast30Days().stream()
                .map(r -> Map.of("date", r[0], "count", r[1]))
                .toList();
    }
}