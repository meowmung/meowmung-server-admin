package com.example.admin.service;

import com.example.admin.dto.InsuranceCountDTO;
import com.example.admin.dto.TermCountDTO;
import com.example.admin.entity.RecommendedResults;
import com.example.admin.entity.RecommendedTerms;
import com.example.admin.repository.RecommendedResultsRepository;
import com.example.admin.repository.RecommendedTermsRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final RecommendedResultsRepository recommendedResultsRepository;
    private final RecommendedTermsRepository recommendedTermsRepository;

    public List<InsuranceCountDTO> findInsuranceCompany() {
        List<InsuranceCountDTO> allGroup = recommendedResultsRepository.findAllGroup();
        return allGroup;
    }

    public List<TermCountDTO> getTopRecommendedTerms() {
        List<RecommendedTerms> terms = recommendedTermsRepository.findAll();
        return terms.stream()
                .map(term -> new TermCountDTO(term.getRecommendedResults().getInsuranceId(), term.getTermId(), 1L))
                .collect(Collectors.toList());
    }

    public List<TermCountDTO> getAllTermsGroupedByResultsId(String insurancdId) {
        return recommendedTermsRepository.findAllGroupedByResultsId(insurancdId);
    }
}
