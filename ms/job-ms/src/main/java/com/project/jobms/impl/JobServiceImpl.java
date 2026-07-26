package com.project.jobms.impl;

import com.project.jobms.Job;
import com.project.jobms.JobRepository;
import com.project.jobms.JobService;
import com.project.jobms.JobWithCompanyDto;
import com.project.jobms.external.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private Job findJobById(Long id) {
        return jobRepository.findById(id).orElseGet(() -> null);
    }

    @Override
    public List<JobWithCompanyDto> findAll() {
        List<JobWithCompanyDto> result = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        var jobs = jobRepository.findAll();

        var companyMap = jobs.stream().map(job -> job.getCompanyId()).distinct()
                .filter(companyId -> companyId != null)
                .map(companyId -> restTemplate
                        .getForObject("http://localhost:8081/companies/"+companyId, Company.class))
                .collect(Collectors.toMap(Company::id, Function.identity()));

        jobs.forEach(job -> result.add(new JobWithCompanyDto(job, companyMap.get(job.getCompanyId()))));

        return result;
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
