package com.example.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommended_results")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommended_results_id")
    private Long id;

    @Column(name = "level")
    private int level;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(name = "insurance_id")
    private String insuranceId;

    @OneToMany(mappedBy = "recommendedResults")
    @Builder.Default
    private List<RecommendedTerms> recommendedTerms = new ArrayList<>();

    public void addRecommendedTerms(RecommendedTerms term) {
        this.recommendedTerms.add(term);
    }
}