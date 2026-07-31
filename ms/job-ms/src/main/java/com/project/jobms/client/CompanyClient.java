package com.project.jobms.client;

import com.project.jobms.external.Company;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-ms")
public interface CompanyClient {
    @GetMapping("/companies/{id}")
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "fetchCompanyFallback")
    Company getCompanyById(@PathVariable Long id);

   default Company fetchCompanyFallback(Long companyId, Exception ex) {
        return new Company(companyId, "Company service is not accessible", null);
    }
}
