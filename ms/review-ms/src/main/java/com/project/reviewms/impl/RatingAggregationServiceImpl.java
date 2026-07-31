package com.project.reviewms.impl;

import com.project.reviewms.CompanyRatingSummary;
import com.project.reviewms.RatingAggregationService;
import com.project.reviewms.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingAggregationServiceImpl implements RatingAggregationService {

    private final ReviewRepository reviewRepository;

    @Override
    public CompanyRatingSummary calculate(Long companyId) {
        return reviewRepository.getCompanyRatingSummary(companyId);
    }
}
