package com.project.reviewms;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCompanyId(Long companyId);

    Optional<Review> findByCompanyIdAndId(Long companyId, Long id);

    boolean existsByCompanyIdAndId(Long companyId, Long id);

    @Query("select r.companyId as companyId,  avg(r.rating) as averageRating, count(*) as reviewCount from Review as r where r.companyId = :companyId group by r.companyId")
    CompanyRatingSummary getCompanyRatingSummary(@Param("companyId") Long companyId);
}
