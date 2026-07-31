package com.project.reviewms.impl;

import com.project.reviewms.*;
import com.project.reviewms.event.CompanyRatingUpdatedEvent;
import com.project.reviewms.outbox.OutboxService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RatingAggregationService ratingAggregationService;
    private final OutboxService outboxService;

    @Override
    public List<Review> findAllByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    @Transactional
    public boolean create(Long companyId, Review review) {
        if(companyId != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            var ratingSummary = ratingAggregationService.calculate(companyId);
            if(ratingSummary != null) {
            var event = CompanyRatingUpdatedEvent.builder()
                    .eventId(UUID.randomUUID())
                    .averageRating(ratingSummary.getAverageRating())
                    .reviewCount(ratingSummary.getReviewCount())
                    .occurredAt(Instant.now())
                    .companyId(companyId)
                    .build();

            outboxService.saveEvent(event);

            }

            return true;
        }
        return false;
    }

    @Override
    public Review getById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean deleteById(Long reviewId) {
        if(reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(Long reviewId, Review review) {
        var reviewEntity = reviewRepository.findById(reviewId).orElse(null);
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
