package com.example.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "additional_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AdditionalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_info_id")
    private Long id;

    @Column(name = "additional_info_weight", nullable = false)
    private Integer weight;

    @Column(name = "additional_info_food_count", nullable = false)
    private Integer foodCount;

    @Column(name = "additional_info_current_disease", nullable = true)
    private String currentDisease;

}