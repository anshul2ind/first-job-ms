package com.project.jobms.dto;

import com.project.jobms.external.Company;
import com.project.jobms.external.Review;

import java.util.List;

public record JobDetailsResponseDto(JobDto job, Company company, List<Review> reviews) {

}
