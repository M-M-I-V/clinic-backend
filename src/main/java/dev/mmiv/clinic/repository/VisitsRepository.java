package dev.mmiv.clinic.repository;

import dev.mmiv.clinic.entity.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.time.LocalDate;

@Repository
public interface VisitsRepository extends JpaRepository<Visits, Integer>, JpaSpecificationExecutor<Visits> {
  
  @Query("SELECT COUNT(v) FROM Visits v WHERE v.visitDate = CURRENT_DATE")
  long countTodayVisits();

  @Query("SELECT COUNT(v) FROM Visits v WHERE FUNCTION('MONTH', v.visitDate) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', v.visitDate) = FUNCTION('YEAR', CURRENT_DATE)")
  long countMonthVisits();

  @Query("SELECT v.diagnosis, COUNT(v) " +
          "FROM Visits v " +
          "WHERE FUNCTION('MONTH', v.visitDate) = FUNCTION('MONTH', CURRENT_DATE) " +
          "AND FUNCTION('YEAR', v.visitDate) = FUNCTION('YEAR', CURRENT_DATE) " +
          "GROUP BY v.diagnosis " +
          "ORDER BY COUNT(v) DESC")
  List<Object[]> countTopDiagnosesThisMonth();

  @Query("SELECT v.visitDate, COUNT(v) " +
          "FROM Visits v " +
          "WHERE v.visitDate >= :cutoffDate " +
          "GROUP BY v.visitDate " +
          "ORDER BY v.visitDate")
  List<Object[]> countVisitsTrendLast30Days(LocalDate cutoffDate);
}