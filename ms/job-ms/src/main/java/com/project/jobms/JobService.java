package com.project.jobms;

import com.project.jobms.dto.JobWithCompanyDto;

import java.util.List;

public interface JobService {
    public List<JobWithCompanyDto> findAll();
    public String createJob(Job job);
    public JobWithCompanyDto getJobById(Long id);
    public boolean deleteJobById(Long id);
    public boolean updateJobById(Long id, Job updateJob);
}
