package dev.mmiv.clinic.repository;

import dev.mmiv.clinic.entity.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Integer>, JpaSpecificationExecutor<Visits> {
}
