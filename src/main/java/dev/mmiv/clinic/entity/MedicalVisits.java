package dev.mmiv.clinic.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MedicalVisits extends Visits {

    private String hama;
    private String referralForm;
    private String medicalChartImage;
}
