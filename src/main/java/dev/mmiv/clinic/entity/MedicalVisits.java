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

    private boolean hama;
    private boolean referralForm;
    private String medicalChartImage;
}
