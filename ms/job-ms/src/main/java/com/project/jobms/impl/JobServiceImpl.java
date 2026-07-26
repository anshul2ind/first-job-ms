package com.project.jobms.impl;

import com.project.jobms.Job;
import com.project.jobms.JobRepository;
import com.project.jobms.JobService;
import com.project.jobms.dto.JobWithCompanyDto;
import com.project.jobms.external.Company;
import com.project.jobms.mapper.JobMapper;
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
    private final RestTemplate restTemplate;

    private Job findJobById(Long id) {
        return jobRepository.findById(id).orElseGet(() -> null);
    }

    private Company fetchCompany(Long companyId) {
        return restTemplate
                .getForObject("http://company-ms/companies/"+companyId, Company.class);
    }

    private

    @Override
    public List<JobWithCompanyDto> findAll() {
        List<JobWithCompanyDto> result = new ArrayList<>();
        var jobs = jobRepository.findAll();

        var companyMap = jobs.stream().map(job -> job.getCompanyId()).distinct()
                .filter(companyId -> companyId != null)
                .map(this::fetchCompany)
                .collect(Collectors.toMap(Company::id, Function.identity()));

        jobs.forEach(job -> result.add(JobMapper.mapToJobWithCompanyDto(job, companyMap.get(job.getCompanyId()))));

        return result;
    }

    @Override
    public String createJob(Job job) {
        job.setId(null);
        jobRepository.save(job);
        return "Job added successfully";
    }

    @Override
    public JobWithCompanyDto getJobById(Long id) {
        var job = findJobById(id);
        if(job != null) {
            if(job.getCompanyId() != null) {
                var company = fetchCompany(job.getCompanyId());
                return JobMapper.mapToJobWithCompanyDto(job, company);
            }
            return JobMapper.mapToJobWithCompanyDto(job, null);

        }
        return null;
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
