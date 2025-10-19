package dev.mmiv.clinic.controller;

import dev.mmiv.clinic.dto.VisitsList;
import dev.mmiv.clinic.entity.Visits;
import dev.mmiv.clinic.service.VisitsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class VisitsController {

    VisitsService visitsService;

    public VisitsController(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    @GetMapping("/visits")
    @PreAuthorize("hasAnyRole('MD', 'DMD', 'NURSE')")
    public ResponseEntity<List<Visits>> getVisits() {
        return new ResponseEntity<>(visitsService.getVisits(), HttpStatus.OK);
    }

    @GetMapping("/visits-list")
    @PreAuthorize("hasAnyRole('MD', 'DMD', 'NURSE')")
    public ResponseEntity<List<VisitsList>> getVisitsList() {
        return ResponseEntity.ok(visitsService.getVisitsList());
    }
}