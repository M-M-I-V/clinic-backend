package dev.mmiv.clinic.repository;

import dev.mmiv.clinic.entity.DentalVisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentalVisitsRepository extends JpaRepository<DentalVisits, Integer> {
}
