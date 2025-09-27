package dev.mmiv.clinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/kpis")
    public Map<String, Long> getKpis() {
        return dashboardService.getKpis();
    }

    @GetMapping("/top-diagnoses")
    public List<Map<String, Object>> getTopDiagnoses() {
        return dashboardService.getTopDiagnoses();
    }

    @GetMapping("/visits-trend")
    public List<Map<String, Object>> getVisitsTrend() {
        return dashboardService.getVisitsTrend();
    }
}