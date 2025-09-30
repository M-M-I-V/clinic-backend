package dev.mmiv.clinic.repository;

import dev.mmiv.clinic.entity.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Integer>, JpaSpecificationExecutor<Visits> {
  
  @Query("SELECT COUNT(v) FROM Visits v WHERE DATE(v.visitDate) = CURRENT_DATE")
    long countTodayVisits();

    @Query("SELECT COUNT(v) FROM Visits v WHERE MONTH(v.visitDate) = MONTH(CURRENT_DATE) AND YEAR(v.visitDate) = YEAR(CURRENT_DATE)")
    long countMonthVisits();

    @Query("SELECT v.diagnosis, COUNT(v) FROM Visits v WHERE MONTH(v.visitDate) = MONTH(CURRENT_DATE) AND YEAR(v.visitDate) = YEAR(CURRENT_DATE) GROUP BY v.diagnosis ORDER BY COUNT(v) DESC")
    List<Object[]> countTopDiagnosesThisMonth();

    @Query("SELECT DATE(v.visitDate), COUNT(v) FROM Visits v WHERE v.visitDate >= CURRENT_DATE - 30 GROUP BY DATE(v.visitDate) ORDER BY DATE(v.visitDate)")
    List<Object[]> countVisitsTrendLast30Days();
}