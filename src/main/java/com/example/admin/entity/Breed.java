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
@Table(name = "breed")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breed_id", nullable = false)
    private Long id;

    //    @Id
    @Column(name = "breed_name", nullable = false, unique = true)
    private String name;

    //    @Id
    @Column(name = "breed_code", nullable = false)
    private Integer code;

    @Column(name = "pet_type", nullable = false)
    private String type;


}