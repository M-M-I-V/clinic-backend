package dev.mmiv.clinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Patients {

    @Id
    private int id;

    @Column(unique = true, nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String fullName;

    private LocalDate birthDate;
    private String gender;
    private int age;

    private String program;
    private String contactNumber;

    private String EmergencyContactName;
    private String EmergencyContactNumber;
    private String EmergencyContactRelationship;

    private String knownDiseases;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visits> visits = new ArrayList<>();
}
