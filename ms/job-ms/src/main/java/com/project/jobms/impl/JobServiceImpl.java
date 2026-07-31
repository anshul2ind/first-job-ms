package com.project.jobms.impl;

import com.project.jobms.Job;
import com.project.jobms.JobRepository;
import com.project.jobms.JobService;
import com.project.jobms.client.CompanyClient;
import com.project.jobms.client.ReviewClient;
import com.project.jobms.dto.JobDetailsResponseDto;
import com.project.jobms.dto.JobWithCompanyDto;
import com.project.jobms.external.Company;
import com.project.jobms.external.Review;
import com.project.jobms.mapper.JobMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;
    private final JobMapper jobMapper;
    private final RestTemplate restTemplate;

    private Job findJobById(Long id) {
        return jobRepository.findById(id).orElseGet(() -> null);
    }

    private Company fetchCompany(Long companyId) {
        if(companyId == null)
            return null;

        return companyClient.getCompanyById(companyId);
    }

    private List<Review> fetchReviews(Long companyId) {
        if(companyId == null)
            return Collections.emptyList();

        return reviewClient.getCompanyReviews(companyId);
    }
//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "findAllFallback")
    @RateLimiter(name = "getAllJobsRateLimiter", fallbackMethod = "findAllRateLimiterFallback")
    @Override
    public List<JobDetailsResponseDto> findAll() {
        List<JobDetailsResponseDto> result = new ArrayList<>();
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
                jobMapper
                        .mapToJobDetailsResponseDto(job, companyMap.get(job.getCompanyId()), reviewsMap.getOrDefault(job.getCompanyId(), List.of()))));

        return result;
    }

    List<JobDetailsResponseDto> findAllFallback(Exception ex) {
        List<JobDetailsResponseDto> result = new ArrayList<>();
        var job = new Job();
        job.setTitle("Company or Review Service  not accessible");
        result.add(jobMapper.mapToJobDetailsResponseDto(job, null, Collections.emptyList()));
        return result;
    }

    List<JobDetailsResponseDto> findAllRateLimiterFallback(Exception ex) {
        List<JobDetailsResponseDto> result = new ArrayList<>();
        var job = new Job();
        job.setTitle("API Rate limit exhausted accessible");
        result.add(jobMapper.mapToJobDetailsResponseDto(job, null, Collections.emptyList()));
        return result;
    }

    @Override
    public String createJob(Job job) {
        job.setId(null);
        jobRepository.save(job);
        return "Job added successfully";
    }

    @Override
    public JobDetailsResponseDto getJobById(Long id) {
        var job = findJobById(id);
        if(job != null) {
            if(job.getCompanyId() != null) {
                var company = fetchCompany(job.getCompanyId());
                var reviews = fetchReviews(job.getCompanyId());
                return jobMapper.mapToJobDetailsResponseDto(job, company, reviews);
            }
            return jobMapper.mapToJobDetailsResponseDto(job, null, List.of());

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
