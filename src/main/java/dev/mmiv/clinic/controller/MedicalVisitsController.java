package dev.mmiv.clinic.controller;

import dev.mmiv.clinic.entity.MedicalVisits;
import dev.mmiv.clinic.service.MedicalVisitsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.List;

@RestController
@RequestMapping("/api/visits")
@CrossOrigin
public class MedicalVisitsController {

    MedicalVisitsService medicalVisitsService;

    MedicalVisitsController(MedicalVisitsService medicalVisitsService) {
        this.medicalVisitsService = medicalVisitsService;
    }

    @GetMapping("/medical-visits")
    @PreAuthorize("hasAnyRole('MD', 'DMD', 'NURSE')")
    public ResponseEntity<List<MedicalVisits>> getMedicalVisits() {
        return new ResponseEntity<>(medicalVisitsService.getMedicalVisits(), HttpStatus.OK);
    }

    @GetMapping("/medical-visits/{id}")
    @PreAuthorize("hasAnyRole('MD', 'DMD', 'NURSE')")
    public ResponseEntity<MedicalVisits> getMedicalVisitById(@PathVariable int id) {
        MedicalVisits medicalVisits = medicalVisitsService.getMedicalVisitById(id);
        
        return medicalVisits != null 
          ? new ResponseEntity<>(medicalVisits, HttpStatus.OK)
          : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/add-medical-visit")
    @PreAuthorize("hasRole('MD')")
    public ResponseEntity<String> createMedicalVisits (
            @RequestParam("multipartFile") MultipartFile multipartFile,
            @RequestParam("visitDate") String visitDate,
            @RequestParam("visitType") String visitType,
            @RequestParam("chiefComplaint") String chiefComplaint,
            @RequestParam(value = "temperature", required = false) String temperature,
            @RequestParam(value = "bloodPressure", required = false) String bloodPressure,
            @RequestParam(value = "pulseRate", required = false) String pulseRate,
            @RequestParam(value = "respiratoryRate", required = false) String respiratoryRate,
            @RequestParam(value = "spo2", required = false) String spo2,
            @RequestParam(value = "history", required = false) String history,
            @RequestParam(value = "symptoms", required = false) String symptoms,
            @RequestParam(value = "physicalExamFindings", required = false) String physicalExamFindings,
            @RequestParam(value = "diagnosis", required = false) String diagnosis,
            @RequestParam(value = "plan", required = false) String plan,
            @RequestParam(value = "treatment", required = false) String treatment,
            @RequestParam("patientId") int patientId,
            @RequestParam("hama") String hama,
            @RequestParam("referralForm") String referralForm
    ) {
        try {
            // Convert String -> LocalDate (frontend should send in "yyyy-MM-dd" format)
            LocalDate parsedVisitDate = LocalDate.parse(visitDate);

            Double parsedTemperature = (temperature != null && !temperature.isBlank()) ? Double.parseDouble(temperature) : null;
            Integer parsedPulseRate = (pulseRate != null && !pulseRate.isBlank()) ? Integer.parseInt(pulseRate) : null;
            Integer parsedRespiratoryRate = (respiratoryRate != null && !respiratoryRate.isBlank()) ? Integer.parseInt(respiratoryRate) : null;
            Double parsedSpo2 = (spo2 != null && !spo2.isBlank()) ? Double.parseDouble(spo2) : null;

            medicalVisitsService.createMedicalVisits(
                    multipartFile,
                    parsedVisitDate,
                    visitType,
                    chiefComplaint,
                    parsedTemperature,
                    bloodPressure,
                    parsedPulseRate,
                    parsedRespiratoryRate,
                    parsedSpo2,
                    history,
                    symptoms,
                    physicalExamFindings,
                    diagnosis,
                    plan,
                    treatment,
                    patientId,
                    hama,
                    referralForm
            );

            String msg = String.format("Visit added for patient ID %d on %s (%s)", 
                    patientId, parsedVisitDate, visitType);
            return ResponseEntity.ok(msg);

        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Use yyyy-MM-dd", e);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number format", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", e);
        }
    }
    
    /* private static LocalDate getLocalDate(String visitDate) {
        LocalDate parsedVisitDate = null;
        
        if(visitDate != null && !visitDate.isBlank()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                parsedVisitDate = LocalDate.parse(visitDate, formatter);
                
            } catch(DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Please use YYYY-MM-DD format");
            }
        }
        return parsedVisitDate;
    } */
    
    @PutMapping("/update-medical-visit/{id}")
    @PreAuthorize("hasRole('MD')")
    public ResponseEntity<String> createMedicalVisits (
            @PathVariable int id,
            @RequestParam("multipartFile") MultipartFile multipartFile,
            @RequestParam("visitDate") String visitDate,
            @RequestParam("visitType") String visitType,
            @RequestParam("chiefComplaint") String chiefComplaint,
            @RequestParam(value = "temperature", required = false) String temperature,
            @RequestParam(value = "bloodPressure", required = false) String bloodPressure,
            @RequestParam(value = "pulseRate", required = false) String pulseRate,
            @RequestParam(value = "respiratoryRate", required = false) String respiratoryRate,
            @RequestParam(value = "spo2", required = false) String spo2,
            @RequestParam(value = "history", required = false) String history,
            @RequestParam(value = "symptoms", required = false) String symptoms,
            @RequestParam(value = "physicalExamFindings", required = false) String physicalExamFindings,
            @RequestParam(value = "diagnosis", required = false) String diagnosis,
            @RequestParam(value = "plan", required = false) String plan,
            @RequestParam(value = "treatment", required = false) String treatment,
            @RequestParam("patientId") int patientId,
            @RequestParam("hama") String hama,
            @RequestParam("referralForm") String referralForm
    ) {
        try {
            // Convert String -> LocalDate (frontend should send in "yyyy-MM-dd" format)
            LocalDate parsedVisitDate = LocalDate.parse(visitDate);

            Double parsedTemperature = (temperature != null && !temperature.isBlank()) ? Double.parseDouble(temperature) : null;
            Integer parsedPulseRate = (pulseRate != null && !pulseRate.isBlank()) ? Integer.parseInt(pulseRate) : null;
            Integer parsedRespiratoryRate = (respiratoryRate != null && !respiratoryRate.isBlank()) ? Integer.parseInt(respiratoryRate) : null;
            Double parsedSpo2 = (spo2 != null && !spo2.isBlank()) ? Double.parseDouble(spo2) : null;

            medicalVisitsService.createMedicalVisits(
                    multipartFile,
                    parsedVisitDate,
                    visitType,
                    chiefComplaint,
                    parsedTemperature,
                    bloodPressure,
                    parsedPulseRate,
                    parsedRespiratoryRate,
                    parsedSpo2,
                    history,
                    symptoms,
                    physicalExamFindings,
                    diagnosis,
                    plan,
                    treatment,
                    patientId,
                    hama,
                    referralForm
            );

            return ResponseEntity.ok("Medical visit successfully updated.");

        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Use yyyy-MM-dd", e);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number format", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred", e);
        }
    }

    @DeleteMapping("/delete-medical-visit/{id}")
    @PreAuthorize("hasRole('MD')")
    public ResponseEntity<String> deleteMedicalVisitById(@PathVariable int id) {
        try {
            medicalVisitsService.deleteMedicalVisits(id);
            return ResponseEntity.ok().build();

        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());

        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getCause());
        }
    }
}