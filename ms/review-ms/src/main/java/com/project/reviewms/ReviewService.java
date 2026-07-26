package com.project.reviewms;

import java.util.List;

public interface ReviewService {
    public List<Review> findAllByCompanyId(Long companyId);
    public boolean create(Long companyId, Review review);
    public Review getById(Long id);
    public Review getByCompanyIdAndReviewId(Long companyId, Long reviewId);
    public boolean deleteByCompanyIdAndReviewId(Long companyId, Long reviewId);
    public boolean updateByCompanyIdAndReviewId(Long companyId, Long reviewId, Review review);
}
