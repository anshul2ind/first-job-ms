package com.project.jobms.mapper;

import com.project.jobms.Job;
import com.project.jobms.dto.JobDetailsResponseDto;
import com.project.jobms.dto.JobDto;
import com.project.jobms.external.Company;
import com.project.jobms.external.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {
    default JobDetailsResponseDto mapToJobDetailsResponseDto(Job job, Company company, List<Review> reviews) {
        return new JobDetailsResponseDto(mapToJobDto(job), company, reviews);
    }

    JobDto mapToJobDto(Job job);
}
