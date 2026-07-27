package com.project.jobms.client;

import com.project.jobms.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-ms")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getCompanyReviews(@RequestParam(name = "companyId") Long companyId);
}
