package dev.mmiv.clinic.service;

import dev.mmiv.clinic.dto.VisitsList;
import dev.mmiv.clinic.entity.Patients;
import dev.mmiv.clinic.entity.Visits;
import dev.mmiv.clinic.repository.VisitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitsService {

    private final VisitsRepository visitsRepository;

    public VisitsService(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }

    public List<VisitsList> getVisitsList() {
        return visitsRepository.findAll().stream()
                .map(v -> new VisitsList(
                        v.getId(),
                        buildFullName(v.getPatient()),
                        v.getPatient().getBirthDate(),
                        v.getVisitDate(),
                        v.getVisitType().name(),
                        v.getSymptoms(),
                        v.getPhysicalExamFindings(),
                        v.getDiagnosis(),
                        v.getTreatment()
                ))
                .toList();
    }

    private String buildFullName(Patients p) {
        if (p == null) return "";
        String mi = (p.getMiddleInitial() != null && !p.getMiddleInitial().isBlank())
                ? " " + p.getMiddleInitial() + "."
                : "";
        return p.getFirstName() + mi + " " + p.getLastName();
    }

    public List<Visits> getVisits() {
        return visitsRepository.findAll();
    }
}