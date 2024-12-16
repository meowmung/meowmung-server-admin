package com.example.admin.repository;

    import com.example.admin.dto.InsuranceCountDTO;
import com.example.admin.entity.RecommendedResults;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecommendedResultsRepository extends JpaRepository<RecommendedResults, Long> {

    @Query("""
        SELECT new com.example.admin.dto.InsuranceCountDTO(rr.insuranceId, count(rr.insuranceId))
        FROM RecommendedResults rr
        GROUP BY rr.insuranceId
        """)
    List<InsuranceCountDTO> findAllGroup();

}
