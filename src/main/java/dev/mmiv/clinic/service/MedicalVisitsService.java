package dev.mmiv.clinic.service;

import dev.mmiv.clinic.dto.MedicalVisitRequest;
import dev.mmiv.clinic.dto.MedicalVisitResponse;
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
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class MedicalVisitsService {

    private final MedicalVisitsRepository medicalVisitsRepository;
    private final PatientsRepository patientsRepository;

    public MedicalVisitsService(MedicalVisitsRepository medicalVisitsRepository,
                                PatientsRepository patientsRepository) {
        this.medicalVisitsRepository = medicalVisitsRepository;
        this.patientsRepository = patientsRepository;
    }

    public List<MedicalVisits> getMedicalVisits() {
        return medicalVisitsRepository.findAll();
    }

    public MedicalVisits getMedicalVisitById(int id) {
        return medicalVisitsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical visit not found"));
    }

    public MedicalVisitResponse getMedicalVisitResponseById(int id) {
        MedicalVisits visit = getMedicalVisitById(id);
        Patients p = visit.getPatient();

        return new MedicalVisitResponse(
                visit.getId(),
                visit.getVisitDate(),
                visit.getVisitType().name(),
                visit.getChiefComplaint(),
                visit.getTemperature(),
                visit.getBloodPressure(),
                visit.getPulseRate(),
                visit.getRespiratoryRate(),
                visit.getSpo2(),
                visit.getHistory(),
                visit.getSymptoms(),
                visit.getPhysicalExamFindings(),
                visit.getDiagnosis(),
                visit.getPlan(),
                visit.getTreatment(),
                visit.getHama(),
                visit.getReferralForm(),
                visit.getMedicalChartImage(),
                p.getFirstName() + " " + p.getLastName(),
                p.getBirthDate()
        );
    }

    public void createMedicalVisits(MultipartFile multipartFile, MedicalVisitRequest dto) throws IOException {
        MedicalVisits medicalVisits = new MedicalVisits();
        saveOrUpdateMedicalVisit(medicalVisits, multipartFile, dto);
    }

    public void updateMedicalVisits(int id, MultipartFile multipartFile, MedicalVisitRequest dto) throws IOException {
        MedicalVisits medicalVisits = medicalVisitsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical visit not found"));
        saveOrUpdateMedicalVisit(medicalVisits, multipartFile, dto);
    }

    public void deleteMedicalVisits(int id) {
        if (medicalVisitsRepository.existsById(id)) {
            medicalVisitsRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical visit not found");
        }
    }

    private void saveOrUpdateMedicalVisit(MedicalVisits medicalVisits,
                                          MultipartFile multipartFile,
                                          MedicalVisitRequest dto) throws IOException {
        try {
            Patients patient = patientsRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

            medicalVisits.setVisitDate(LocalDate.parse(dto.getVisitDate()));
            medicalVisits.setVisitType(VisitType.valueOf(dto.getVisitType().toUpperCase()));
            medicalVisits.setChiefComplaint(dto.getChiefComplaint());
            medicalVisits.setTemperature(parseDouble(dto.getTemperature()));
            medicalVisits.setBloodPressure(dto.getBloodPressure());
            medicalVisits.setPulseRate(parseInt(dto.getPulseRate()));
            medicalVisits.setRespiratoryRate(parseInt(dto.getRespiratoryRate()));
            medicalVisits.setSpo2(parseDouble(dto.getSpo2()));
            medicalVisits.setHistory(dto.getHistory());
            medicalVisits.setSymptoms(dto.getSymptoms());
            medicalVisits.setPhysicalExamFindings(dto.getPhysicalExamFindings());
            medicalVisits.setDiagnosis(dto.getDiagnosis());
            medicalVisits.setPlan(dto.getPlan());
            medicalVisits.setTreatment(dto.getTreatment());
            medicalVisits.setHama(dto.getHama());
            medicalVisits.setReferralForm(dto.getReferralForm());
            medicalVisits.setPatient(patient);

            String imageFile = saveUploadedFile(multipartFile);
            if (imageFile != null) {
                medicalVisits.setMedicalChartImage(imageFile);
            }

            medicalVisitsRepository.save(medicalVisits);

        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Use yyyy-MM-dd", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid visit type: " + dto.getVisitType(), e);
        }
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

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid numeric value: " + value);
        }
    }

    private Integer parseInt(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid integer value: " + value);
        }
    }
}