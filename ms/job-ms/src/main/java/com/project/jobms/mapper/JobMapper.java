package com.project.jobms.mapper;

import com.project.jobms.Job;
import com.project.jobms.dto.JobWithCompanyDto;
import com.project.jobms.external.Company;

public class JobMapper {
    public static JobWithCompanyDto mapToJobWithCompanyDto(Job job, Company company) {
        if(job != null) {
            return JobWithCompanyDto.builder()
                    .id(job.getId())
                    .title(job.getTitle())
                    .description(job.getDescription())
                    .minSalary(job.getMinSalary())
                    .maxSalary(job.getMaxSalary())
                    .location(job.getLocation())
                    .company(company)
                    .build();
        }

        return null;
    }
}
