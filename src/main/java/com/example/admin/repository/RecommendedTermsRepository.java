package com.example.admin.repository;

import com.example.admin.dto.TermCountDTO;
import com.example.admin.entity.RecommendedTerms;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendedTermsRepository extends JpaRepository<RecommendedTerms, Long> {
//    @Query("""
//        SELECT new com.example.admin.dto.TermCountDTO(rt.termId, COUNT(rt.termId))
//        FROM RecommendedTerms rt
//        WHERE rt.recommendedResults.id = :resultsId
//        GROUP BY rt.termId
//        ORDER BY COUNT(rt.termId) DESC
//    """)
//    List<TermCountDTO> findTopRecommendedTermsByResultsId(Long resultsId);

    // 이승진
//    List<RecommendedTerms> findByRecommendedResultsId(Long recommendedResultsId);
    List<RecommendedTerms> findAll();

    @Query("""
                SELECT new com.example.admin.dto.TermCountDTO(rt.recommendedResults.insuranceId, rt.termId, COUNT(rt.termId))
                    FROM RecommendedTerms rt
                    WHERE rt.recommendedResults.insuranceId = :insuranceId
                    GROUP BY rt.recommendedResults.insuranceId, rt.termId
                    ORDER BY rt.recommendedResults.insuranceId, COUNT(rt.termId) DESC
            """)
    List<TermCountDTO> findAllGroupedByResultsId(@Param("insuranceId") String insuranceId);

//    @Query("""
//                SELECT new com.example.admin.dto.TermCountDTO(rt.recommendedResults.insuranceId, rt.termId, COUNT(rt.termId))
//                FROM RecommendedTerms rt
//                GROUP BY rt.recommendedResults.id, rt.termId
//                ORDER BY rt.recommendedResults.id, COUNT(rt.termId) DESC
//            """)
//    List<TermCountDTO> findAllGroupedByResultsId(Long insuranceId);

}
