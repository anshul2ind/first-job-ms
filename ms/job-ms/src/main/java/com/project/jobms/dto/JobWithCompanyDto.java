package com.project.jobms.dto;

import com.project.jobms.external.Company;
import com.project.jobms.external.Review;

import java.util.List;

public record JobWithCompanyDto(Long id, String title, String description, String minSalary, String maxSalary, String location, Company company, List<Review> reviews) {
}
