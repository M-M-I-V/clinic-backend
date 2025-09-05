package dev.mmiv.clinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public abstract class Visits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate visitDate;

    @Enumerated(EnumType.STRING)
    private VisitType visitType;

    @Column(nullable = false)
    private String chiefComplaint;

    private Double temperature;
    private String bloodPressure;
    private int pulseRate;
    public int respiratoryRate;
    private Double spo2;

    @Column(columnDefinition = "TEXT")
    private String history;

    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String physicalExamFindings;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String plan;

    @Column(columnDefinition = "TEXT")
    private String treatment;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;
}
