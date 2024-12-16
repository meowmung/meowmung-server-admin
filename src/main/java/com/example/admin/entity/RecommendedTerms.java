package com.example.admin.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recommended_terms")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecommendedTerms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommended_terms_id")
    private Long id;

    @Column(name = "term_id")
    private String termId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recommended_results_id")
    private RecommendedResults recommendedResults;

    public void addRecomendedResults(RecommendedResults recommendedResult) {
        this.recommendedResults = recommendedResult;
    }
}