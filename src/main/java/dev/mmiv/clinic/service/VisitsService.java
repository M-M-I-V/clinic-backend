package dev.mmiv.clinic.service;

import dev.mmiv.clinic.dto.VisitsList;
import dev.mmiv.clinic.entity.Visits;
import dev.mmiv.clinic.repository.VisitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitsService {

    VisitsRepository visitsRepository;

    public VisitsService(VisitsRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }

    public List<Visits> getVisits() {
        return visitsRepository.findAll();
    }

    public List<VisitsList> getVisitsList() {
        return visitsRepository.findAll().stream()
                .map(v -> new VisitsList(
                        v.getId(),
                        v.getVisitDate(),
                        v.getVisitType().name(),
                        v.getChiefComplaint(),
                        v.getDiagnosis()
                ))
                .toList();
    }
}
