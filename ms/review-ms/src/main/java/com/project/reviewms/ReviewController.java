package com.project.reviewms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping()
    public ResponseEntity<List<Review>> findAllByCompanyId(@RequestParam(required = true) Long companyId) {
        return ResponseEntity.ok(reviewService.findAllByCompanyId(companyId));
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam(required = true) Long companyId, @RequestBody Review review) {
        var added = reviewService.create(companyId, review);
        return added
                ? ResponseEntity.ok("Review added successfully")
                : new ResponseEntity<>("Review not added", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getByCompanyIdAndReviewId(@PathVariable Long reviewId) {
        var review = reviewService.getById(reviewId);
        return review == null
                ? new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateByCompanyIdAndReviewId(@PathVariable Long reviewId, @RequestBody Review review) {
        var updated = reviewService.updateById(reviewId, review);
        return updated
                ? ResponseEntity.ok("Review updated successfully")
                : new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteByCompanyIdAndReviewId(@PathVariable Long reviewId) {
        var deleted = reviewService.deleteById(reviewId);
        return deleted
                ? ResponseEntity.ok("Review deleted successfully")
                : new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }
}
