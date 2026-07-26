package com.project.jobms.dto;

import com.project.jobms.external.Company;
import lombok.Builder;

@Builder
public record JobWithCompanyDto(Long id, String title, String description, String minSalary, String maxSalary, String location, Company company) {
}
