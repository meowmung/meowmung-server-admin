package com.example.admin.repository;

import com.example.admin.dto.TermCountDTO;
import com.example.admin.entity.RecommendedTerms;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecommendedTermsRepository extends JpaRepository<RecommendedTerms, Long> {
//    @Query("""
//        SELECT new com.example.admin.dto.TermCountDTO(rt.termId, COUNT(rt.termId))
//        FROM RecommendedTerms rt
//        WHERE rt.recommendedResults.id = :resultsId
//        GROUP BY rt.termId
//        ORDER BY COUNT(rt.termId) DESC
//    """)
//    List<TermCountDTO> findTopRecommendedTermsByResultsId(Long resultsId);
List<RecommendedTerms> findByRecommendedResultsId(Long recommendedResultsId);
    @Query("""
        SELECT new com.example.admin.dto.TermCountDTO(rt.recommendedResults.id, rt.termId, COUNT(rt.termId))
        FROM RecommendedTerms rt
        GROUP BY rt.recommendedResults.id, rt.termId
        ORDER BY rt.recommendedResults.id, COUNT(rt.termId) DESC
    """)
    List<TermCountDTO> findAllGroupedByResultsId();
}
