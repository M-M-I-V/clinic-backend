package dev.mmiv.clinic.service;

import dev.mmiv.clinic.entity.MedicalVisits;
import dev.mmiv.clinic.entity.VisitType;
import dev.mmiv.clinic.repository.MedicalVisitsRepository;
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
public class MedicalVisitsService {

    MedicalVisitsRepository medicalVisitsRepository;

    public MedicalVisitsService(MedicalVisitsRepository medicalVisitsRepository) {
        this.medicalVisitsRepository = medicalVisitsRepository;
    }

    public void createMedicalVisits(MultipartFile multipartFile,
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
                                    String treatment,
                                    int patientId,
                                    String hama,
                                    String referralForm) throws IOException {

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

        MedicalVisits medicalVisits = new MedicalVisits();
        medicalVisits.setVisitDate(visitDate);
        medicalVisits.setVisitType(visitType);
        medicalVisits.setChiefComplaint(chiefComplaint);
        medicalVisits.setTemperature(temperature);
        medicalVisits.setBloodPressure(bloodPressure);
        medicalVisits.setPulseRate(pulseRate);
        medicalVisits.setRespiratoryRate(respiratoryRate);
        medicalVisits.setSpo2(spo2);
        medicalVisits.setHistory(history);
        medicalVisits.setSymptoms(symptoms);
        medicalVisits.setPhysicalExamFindings(physicalExamFindings);
        medicalVisits.setDiagnosis(diagnosis);
        medicalVisits.setPlan(plan);
        medicalVisits.setTreatment(treatment);
        medicalVisits.setPatientId(patientId);
        medicalVisits.setHama(hama);
        medicalVisits.setReferralForm(referralForm);
        medicalVisits.setMedicalChartImage("http://localhost:8080/uploads/" + fileName);
        medicalVisitsRepository.save(medicalVisits);
    }

    public List<MedicalVisits> getMedicalVisits() {
        return medicalVisitsRepository.findAll();
    }

    public MedicalVisits getMedicalVisitById(int id) {
        return medicalVisitsRepository.findById(id).orElse(null);
    }

    public void updateMedicalVisits(int id,
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
                                    String treatment,
                                    int patientId,
                                    String hama,
                                    String referralForm) throws IOException {

        MedicalVisits medicalVisits = medicalVisitsRepository.findById(id)
                .orElse(null);

        medicalVisits.setVisitDate(visitDate);
        medicalVisits.setVisitType(visitType);
        medicalVisits.setChiefComplaint(chiefComplaint);
        medicalVisits.setTemperature(temperature);
        medicalVisits.setBloodPressure(bloodPressure);
        medicalVisits.setPulseRate(pulseRate);
        medicalVisits.setRespiratoryRate(respiratoryRate);
        medicalVisits.setSpo2(spo2);
        medicalVisits.setHistory(history);
        medicalVisits.setSymptoms(symptoms);
        medicalVisits.setPhysicalExamFindings(physicalExamFindings);
        medicalVisits.setDiagnosis(diagnosis);
        medicalVisits.setPlan(plan);
        medicalVisits.setTreatment(treatment);
        medicalVisits.setPatientId(patientId);
        medicalVisits.setHama(hama);
        medicalVisits.setReferralForm(referralForm);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String rootPath = System.getProperty("user.dir");
            String uploadDir = rootPath + "/uploads";

            File uploadDirFile = new File(uploadDir);

            String fileName = multipartFile.getOriginalFilename();
            String uploadPath = uploadDir + "/" + fileName;
            multipartFile.transferTo(new File(uploadPath));

            medicalVisits.setMedicalChartImage("http://localhost:8080/uploads/" + fileName);
        }
        medicalVisitsRepository.save(medicalVisits);
    }

    public void deleteMedicalVisits(int id) {
        if(medicalVisitsRepository.existsById(id)) {
            medicalVisitsRepository.deleteById(id);
        }
    }
}