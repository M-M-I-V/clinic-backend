package dev.mmiv.clinic.repository;

import dev.mmiv.clinic.entity.MedicalVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalVisitsRepository extends JpaRepository<MedicalVisits, Integer> {
}
