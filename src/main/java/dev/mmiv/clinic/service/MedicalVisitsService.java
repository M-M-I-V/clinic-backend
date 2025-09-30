package dev.mmiv.clinic.service;

import dev.mmiv.clinic.entity.MedicalVisits;
import dev.mmiv.clinic.entity.Patients;
import dev.mmiv.clinic.entity.VisitType;
import dev.mmiv.clinic.repository.MedicalVisitsRepository;
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
public class MedicalVisitsService {

    MedicalVisitsRepository medicalVisitsRepository;
    PatientsRepository patientsRepository;

    public MedicalVisitsService(MedicalVisitsRepository medicalVisitsRepository, PatientsRepository patientsRepository) {
        this.medicalVisitsRepository = medicalVisitsRepository;
        this.patientsRepository = patientsRepository;
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

        MedicalVisits medicalVisits = new MedicalVisits();
        saveOrUpdateMedicalVisit(medicalVisits, multipartFile, visitDate, visitType,
                chiefComplaint, temperature, bloodPressure, pulseRate, respiratoryRate,
                spo2, history, symptoms, physicalExamFindings, diagnosis, plan, treatment, patientId,
                hama, referralForm);
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical Visit not found"));
        saveOrUpdateMedicalVisit(medicalVisits, multipartFile, visitDate, visitType,
                chiefComplaint, temperature, bloodPressure, pulseRate, respiratoryRate,
                spo2, history, symptoms, physicalExamFindings, diagnosis, plan, treatment, patientId,
                hama, referralForm);
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

    private void saveOrUpdateMedicalVisit(MedicalVisits medicalVisits,
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
                                          int patientId,
                                          String hama,
                                          String referralForm) throws IOException {

        Patients patients = patientsRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

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
        medicalVisits.setPatient(patients);
        medicalVisits.setHama(hama);
        medicalVisits.setReferralForm(referralForm);

        String imageFile = saveUploadedFile(multipartFile);
        if (imageFile != null) {
            medicalVisits.setMedicalChartImage(imageFile);
        }

        medicalVisitsRepository.save(medicalVisits);
    }

    public void deleteMedicalVisits(int id) {
        if(medicalVisitsRepository.existsById(id)) {
            medicalVisitsRepository.deleteById(id);
        }
    }
}