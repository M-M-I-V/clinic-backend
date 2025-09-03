package dev.mmiv.clinic.repository;

import dev.mmiv.clinic.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer>, JpaSpecificationExecutor<Patients> {
}
