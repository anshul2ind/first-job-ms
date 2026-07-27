package com.project.jobms.client;

import com.project.jobms.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-ms")
public interface CompanyClient {
    @GetMapping("/companies/{id}")
    Company getCompanyById(@PathVariable Long id);
}
