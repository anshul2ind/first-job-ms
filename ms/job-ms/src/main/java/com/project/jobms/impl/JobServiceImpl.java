package com.project.jobms.impl;

import com.project.jobms.Job;
import com.project.jobms.JobRepository;
import com.project.jobms.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private Job findJobById(Long id) {
        return jobRepository.findById(id).orElseGet(() -> null);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public String createJob(Job job) {
        job.setId(null);
        jobRepository.save(job);
        return "Job added successfully";
    }

    @Override
    public Job getJobById(Long id) {
        return findJobById(id);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJobById(Long id, Job updateJob) {
        Job job = findJobById(id);
        if(job != null) {
            job.setTitle(updateJob.getTitle());
            job.setDescription(updateJob.getDescription());
            job.setLocation(updateJob.getLocation());
            job.setMinSalary(updateJob.getMinSalary());
            job.setMaxSalary(updateJob.getMaxSalary());
            jobRepository.save(job);
            return true;
        }

        return false;
    }
}
