package dev.mmiv.clinic.service;

import dev.mmiv.clinic.entity.DentalVisits;
import dev.mmiv.clinic.entity.VisitType;
import dev.mmiv.clinic.repository.DentalVisitsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DentalVisitsService {

    DentalVisitsRepository dentalVisitsRepository;

    public DentalVisitsService(DentalVisitsRepository dentalVisitsRepository) {
        this.dentalVisitsRepository = dentalVisitsRepository;
    }

    public void createDentalVisits(MultipartFile multipartFile,
                                    LocalDate visitDate,
                                    VisitType visitType,
                                    String chiefComplaint,
                                    Double temperature,
                                    String bloodPressure,
                                    int pulseRate,
                                    int respiratoryRate,
                                    Double spo2,
                                    String history,
                                    String symptoms,
                                    String physicalExamFindings,
                                    String diagnosis,
                                    String plan,
                                    String treatment) throws IOException {

        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/uploads";

        File uploadDirFile = new File(uploadDir);

        String fileName = multipartFile.getOriginalFilename();
        String uploadPath = uploadDir + "/" + fileName;

        try {
            multipartFile.transferTo(new File(uploadPath));
            System.out.println("File uploaded to: " + uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save uploaded file", e);
        }

        DentalVisits dentalVisits = new DentalVisits();
        dentalVisits.setVisitDate(visitDate);
        dentalVisits.setVisitType(visitType);
        dentalVisits.setChiefComplaint(chiefComplaint);
        dentalVisits.setTemperature(temperature);
        dentalVisits.setBloodPressure(bloodPressure);
        dentalVisits.setPulseRate(pulseRate);
        dentalVisits.setRespiratoryRate(respiratoryRate);
        dentalVisits.setSpo2(spo2);
        dentalVisits.setHistory(history);
        dentalVisits.setSymptoms(symptoms);
        dentalVisits.setPhysicalExamFindings(physicalExamFindings);
        dentalVisits.setDiagnosis(diagnosis);
        dentalVisits.setPlan(plan);
        dentalVisits.setTreatment(treatment);
        dentalVisits.setDentalChartImage("http://localhost:8080/uploads/" + fileName);
        dentalVisitsRepository.save(dentalVisits);
    }

    public List<DentalVisits> getDentalVisits() {
        return dentalVisitsRepository.findAll();
    }

    public DentalVisits getDentalVisitById(int id) {
        return dentalVisitsRepository.findById(id).orElse(null);
    }

    public void updateDentalVisits(int id,
                                    MultipartFile multipartFile,
                                    LocalDate visitDate,
                                    VisitType visitType,
                                    String chiefComplaint,
                                    Double temperature,
                                    String bloodPressure,
                                    int pulseRate,
                                    int respiratoryRate,
                                    Double spo2,
                                    String history,
                                    String symptoms,
                                    String physicalExamFindings,
                                    String diagnosis,
                                    String plan,
                                    String treatment) throws IOException {

        DentalVisits dentalVisits = dentalVisitsRepository.findById(id)
                .orElse(null);

        dentalVisits.setVisitDate(visitDate);
        dentalVisits.setVisitType(visitType);
        dentalVisits.setChiefComplaint(chiefComplaint);
        dentalVisits.setTemperature(temperature);
        dentalVisits.setBloodPressure(bloodPressure);
        dentalVisits.setPulseRate(pulseRate);
        dentalVisits.setRespiratoryRate(respiratoryRate);
        dentalVisits.setSpo2(spo2);
        dentalVisits.setHistory(history);
        dentalVisits.setSymptoms(symptoms);
        dentalVisits.setPhysicalExamFindings(physicalExamFindings);
        dentalVisits.setDiagnosis(diagnosis);
        dentalVisits.setPlan(plan);
        dentalVisits.setTreatment(treatment);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String rootPath = System.getProperty("user.dir");
            String uploadDir = rootPath + "/uploads";

            File uploadDirFile = new File(uploadDir);

            String fileName = multipartFile.getOriginalFilename();
            String uploadPath = uploadDir + "/" + fileName;
            multipartFile.transferTo(new File(uploadPath));

            dentalVisits.setDentalChartImage("http://localhost:8080/uploads/" + fileName);
        }
        dentalVisitsRepository.save(dentalVisits);
    }

    public void deleteDentalVisits(int id) {
        if(dentalVisitsRepository.existsById(id)) {
            dentalVisitsRepository.deleteById(id);
        }
    }
}
