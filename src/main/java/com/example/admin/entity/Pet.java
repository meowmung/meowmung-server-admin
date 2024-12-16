package com.example.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Pet {

    @Column(name = "user_id")
    private Long userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "pet_age", nullable = false)
    private Integer age;

    @Column(name = "pet_gender", nullable = false)
    private Boolean gender;

    @Column(name = "pet_neutered", nullable = false)
    private Boolean neutered;

    @ManyToOne
//    @JoinColumn(name = "breed_name", nullable = false)
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    @OneToMany(mappedBy = "pet")
    @Builder.Default
    private List<Concerned> concerneds = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "additional_info_id", nullable = true)
    private AdditionalInfo additionalInfo;

//    @OneToMany
//    @JoinColumn(name = "recommended_insurance_id")
//    private List<RecommendedInsurance> recommendedInsurance = new ArrayList<>();

    public void addConcerned(List<Concerned> concerneds) {
        this.concerneds.addAll(concerneds);
    }

    // setter 안쓰려면
    public void addAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
