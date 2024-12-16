package com.example.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermCountDTO {
    private String recommendedResultsId;
    private String termId;
    private Long count; // Example count, adjust based on your needs
}