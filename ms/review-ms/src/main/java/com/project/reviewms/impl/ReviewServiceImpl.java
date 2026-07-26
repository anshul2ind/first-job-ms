package com.project.reviewms.impl;

import com.project.reviewms.Review;
import com.project.reviewms.ReviewRepository;
import com.project.reviewms.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> findAllByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean create(Long companyId, Review review) {
        if(companyId != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review getById(Long id) {
        return null;
    }

    @Override
    public Review getByCompanyIdAndReviewId(Long companyId, Long reviewId) {
        return reviewRepository.findByCompanyIdAndId(companyId, reviewId).orElse(null);
    }

    @Override
    public boolean deleteByCompanyIdAndReviewId(Long companyId, Long reviewId) {
        if(reviewRepository.existsByCompanyIdAndId(companyId, reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateByCompanyIdAndReviewId(Long companyId, Long reviewId, Review review) {
        var reviewEntity = reviewRepository.findByCompanyIdAndId(companyId, reviewId).orElse(null);
        if(reviewEntity != null) {
            reviewEntity.setTitle(review.getTitle());
            reviewEntity.setDescription(review.getDescription());
            reviewEntity.setRating(review.getRating());
            reviewRepository.save(reviewEntity);
            return true;
        }
        return false;
    }
}
