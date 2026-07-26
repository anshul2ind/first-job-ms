package com.project.jobms;

import java.util.List;

public interface JobService {
    public List<JobWithCompanyDto> findAll();
    public String createJob(Job job);
    public Job getJobById(Long id);
    public boolean deleteJobById(Long id);
    public boolean updateJobById(Long id, Job updateJob);
}
