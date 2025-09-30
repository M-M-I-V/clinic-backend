package dev.mmiv.clinic.service;

import dev.mmiv.clinic.entity.DentalVisits;
import dev.mmiv.clinic.entity.Patients;
import dev.mmiv.clinic.entity.VisitType;
import dev.mmiv.clinic.repository.DentalVisitsRepository;
import dev.mmiv.clinic.repository.PatientsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DentalVisitsService {

    private final PatientsRepository patientsRepository;
    DentalVisitsRepository dentalVisitsRepository;

    public DentalVisitsService(DentalVisitsRepository dentalVisitsRepository, PatientsRepository patientsRepository) {
        this.dentalVisitsRepository = dentalVisitsRepository;
        this.patientsRepository = patientsRepository;
    }

    public void createDentalVisits(MultipartFile multipartFile,
                                           LocalDate visitDate,
                                           VisitType visitType,
                                           String chiefComplaint,
                                           Double temperature,
                                           String bloodPressure,
                                           Integer pulseRate,
                                           Integer respiratoryRate,
                                           Double spo2,
                                           String history,
                                           String symptoms,
                                           String physicalExamFindings,
                                           String diagnosis,
                                           String plan,
                                           String treatment,
                                           int patientId) throws IOException {

        DentalVisits dentalVisits = new DentalVisits();
        saveOrUpdateDentalVisit(dentalVisits, multipartFile, visitDate, visitType,
                chiefComplaint, temperature, bloodPressure, pulseRate, respiratoryRate,
                spo2, history, symptoms, physicalExamFindings, diagnosis, plan, treatment, patientId);
    }

    public List<DentalVisits> getDentalVisits() {
        return dentalVisitsRepository.findAll();
    }

    public DentalVisits getDentalVisitById(int id) {
        return dentalVisitsRepository.findById(id).orElse(null);
    }

    public DentalVisits updateDentalVisits(int id,
                                           MultipartFile multipartFile,
                                           LocalDate visitDate,
                                           VisitType visitType,
                                           String chiefComplaint,
                                           Double temperature,
                                           String bloodPressure,
                                           Integer pulseRate,
                                           Integer respiratoryRate,
                                           Double spo2,
                                           String history,
                                           String symptoms,
                                           String physicalExamFindings,
                                           String diagnosis,
                                           String plan,
                                           String treatment,
                                           int patientId) throws IOException {

        DentalVisits dentalVisits = dentalVisitsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dental Visit not found"));
        return saveOrUpdateDentalVisit(dentalVisits, multipartFile, visitDate, visitType,
                chiefComplaint, temperature, bloodPressure, pulseRate, respiratoryRate,
                spo2, history, symptoms, physicalExamFindings, diagnosis, plan, treatment, patientId);
    }

    private String saveUploadedFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String rootPath = System.getProperty("user.dir");
            String uploadDir = rootPath + "/uploads";

            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) uploadDirFile.mkdirs();

            String fileName = multipartFile.getOriginalFilename();
            String uploadPath = uploadDir + "/" + fileName;

            multipartFile.transferTo(new File(uploadPath));
            return "http://localhost:8080/uploads/" + fileName;
        }
        return null;
    }

    private DentalVisits saveOrUpdateDentalVisit(DentalVisits dentalVisits,
                                                 MultipartFile multipartFile,
                                                 LocalDate visitDate,
                                                 VisitType visitType,
                                                 String chiefComplaint,
                                                 Double temperature,
                                                 String bloodPressure,
                                                 Integer pulseRate,
                                                 Integer respiratoryRate,
                                                 Double spo2,
                                                 String history,
                                                 String symptoms,
                                                 String physicalExamFindings,
                                                 String diagnosis,
                                                 String plan,
                                                 String treatment,
                                                 int patientId) throws IOException {

        Patients patients = patientsRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

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
        dentalVisits.setPatient(patients);

        String imageFile = saveUploadedFile(multipartFile);
        if (imageFile != null) {
            dentalVisits.setDentalChartImage(imageFile);
        }

        return dentalVisitsRepository.save(dentalVisits);
    }

    public void deleteDentalVisits(int id) {
        if(dentalVisitsRepository.existsById(id)) {
            dentalVisitsRepository.deleteById(id);
        }
    }
}