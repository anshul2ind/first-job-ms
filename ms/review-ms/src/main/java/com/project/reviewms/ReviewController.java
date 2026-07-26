package com.project.reviewms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("companies/{companyId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping()
    public ResponseEntity<List<Review>> findAllByCompanyId(@PathVariable Long companyId) {
        return ResponseEntity.ok(reviewService.findAllByCompanyId(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@PathVariable Long companyId, @RequestBody Review review) {
        var added = reviewService.create(companyId, review);
        return added
                ? ResponseEntity.ok("Review added successfully")
                : new ResponseEntity<>("Review not added", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getByCompanyIdAndReviewId(@PathVariable Long companyId, @PathVariable Long reviewId) {
        var review = reviewService.getByCompanyIdAndReviewId(companyId, reviewId);
        return review == null
                ? new ResponseEntity<>("Company or Review not found", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateByCompanyIdAndReviewId(@PathVariable Long companyId, @PathVariable Long reviewId, @RequestBody Review review) {
        var updated = reviewService.updateByCompanyIdAndReviewId(companyId, reviewId, review);
        return updated
                ? ResponseEntity.ok("Review updated successfully")
                : new ResponseEntity<>("Company or Review not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteByCompanyIdAndReviewId(@PathVariable Long companyId, @PathVariable Long reviewId) {
        var deleted = reviewService.deleteByCompanyIdAndReviewId(companyId, reviewId);
        return deleted
                ? ResponseEntity.ok("Review deleted successfully")
                : new ResponseEntity<>("Company or Review not found", HttpStatus.NOT_FOUND);
    }
}
