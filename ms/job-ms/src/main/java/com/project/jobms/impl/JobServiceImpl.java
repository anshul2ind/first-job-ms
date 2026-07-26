package com.project.jobms.impl;

import com.project.jobms.Job;
import com.project.jobms.JobRepository;
import com.project.jobms.JobService;
import com.project.jobms.dto.JobWithCompanyDto;
import com.project.jobms.external.Company;
import com.project.jobms.external.Review;
import com.project.jobms.mapper.JobMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        if(companyId == null)
            return null;

        return restTemplate
                .getForObject("http://company-ms/companies/"+companyId, Company.class);
    }

    private List<Review> fetchReviews(Long companyId) {
        if(companyId == null)
            return null;

        ResponseEntity<List<Review>> reviewsResponse = restTemplate.exchange(
                "http://review-ms/reviews?companyId=" + companyId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {}
        );
        return reviewsResponse.getBody();
    }

    @Override
    public List<JobWithCompanyDto> findAll() {
        List<JobWithCompanyDto> result = new ArrayList<>();
        var jobs = jobRepository.findAll();

        var distinctCompanyIds = jobs.stream().map(job -> job.getCompanyId()).distinct()
                .filter(companyId -> companyId != null)
                .collect(Collectors.toSet());

        var companyMap = distinctCompanyIds
                .stream()
                .map(this::fetchCompany)
                .collect(Collectors.toMap(Company::id, Function.identity()));

        var reviewsMap = distinctCompanyIds
                .stream()
                .collect(Collectors.toMap(Function.identity(), this::fetchReviews));

        jobs.forEach(job -> result.add(
                JobMapper
                        .mapToJobWithCompanyDto(job, companyMap.get(job.getCompanyId()), reviewsMap.getOrDefault(job.getCompanyId(), List.of()))));

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
                var reviews = fetchReviews(job.getCompanyId());
                return JobMapper.mapToJobWithCompanyDto(job, company, reviews);
            }
            return JobMapper.mapToJobWithCompanyDto(job, null, List.of());

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
