package dev.mmiv.clinic.controller;

import dev.mmiv.clinic.entity.Patients;
import dev.mmiv.clinic.service.PatientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class PatientsController {

    PatientsService patientsService;

    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @PostMapping("/add-patient")
    public ResponseEntity<String> createPatient(Patients patient) {
        patientsService.createPatient(patient);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patients>> getAllPatients() {
        return new ResponseEntity<>(patientsService.getPatients(), HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patients> getAllPatientsByName(@PathVariable int id) {
        return new ResponseEntity<>(patientsService.getPatientById(id), HttpStatus.OK);
    }

    @PutMapping("/update-patient/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable int id, @RequestBody Patients patient) {
        patientsService.updatePatient(patient);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete-patient/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable int id) {
        try {
            patientsService.deletePatient(id);
            return ResponseEntity.ok().build();

        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
        }
    }
}
