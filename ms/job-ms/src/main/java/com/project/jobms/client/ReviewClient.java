package com.project.jobms.client;

import com.project.jobms.external.Review;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-ms")
public interface ReviewClient {
    @GetMapping("/reviews")
    @Retry(name = "reviewRetry", fallbackMethod = "fetchReviewsFallback")
    List<Review> getCompanyReviews(@RequestParam(name = "companyId") Long companyId);

   default List<Review> fetchReviewsFallback(Long companyId, Exception ex) {
        return List.of(new Review(null, "Review service is not accessible", null, -1));
    }
}
