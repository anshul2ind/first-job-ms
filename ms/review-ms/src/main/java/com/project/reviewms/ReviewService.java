package com.project.reviewms;

import java.util.List;

public interface ReviewService {
    public List<Review> findAllByCompanyId(Long companyId);
    public boolean create(Long companyId, Review review);
    public Review getById(Long reviewId);
    public boolean deleteById(Long reviewId);
    public boolean updateById(Long reviewId, Review review);
}
