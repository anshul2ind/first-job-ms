package com.project.reviewms;

public interface RatingAggregationService {
    CompanyRatingSummary calculate(Long companyId);
}
