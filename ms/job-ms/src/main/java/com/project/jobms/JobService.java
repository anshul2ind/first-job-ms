package com.project.jobms;

import com.project.jobms.dto.JobDetailsResponseDto;
import com.project.jobms.dto.JobWithCompanyDto;

import java.util.List;

public interface JobService {
    public List<JobDetailsResponseDto> findAll();
    public String createJob(Job job);
    public JobDetailsResponseDto getJobById(Long id);
    public boolean deleteJobById(Long id);
    public boolean updateJobById(Long id, Job updateJob);
}
