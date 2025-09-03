package dev.mmiv.clinic.service;

import dev.mmiv.clinic.entity.Patients;
import dev.mmiv.clinic.repository.PatientsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientsService {

    PatientsRepository patientsRepository;

    public PatientsService(PatientsRepository patientsRepository) {
        this.patientsRepository = patientsRepository;
    }

    public void createPatient(Patients patient) {
        patientsRepository.save(patient);
    }

    public List<Patients> getPatients() {
        return patientsRepository.findAll();
    }

    public Patients getPatientById(int id) {
        return patientsRepository.findById(id).orElse(null);
    }

    public void updatePatient(Patients patient) {
        patientsRepository.save(patient);
    }

    public void deletePatient(int id) {
        patientsRepository.deleteById(id);
    }
}
