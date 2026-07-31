package com.project.companyms.impl;

import com.project.companyms.Company;
import com.project.companyms.CompanyRepository;
import com.project.companyms.CompanyService;
import com.project.companyms.event.CompanyRatingUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    private Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public String create(Company company) {
        companyRepository.save(company);
        return "Successfully saved company";
    }

    @Override
    public void updateRating(CompanyRatingUpdatedEvent event) {
        var company = findById(event.companyId());
        if(company != null) {
            company.setAverageRating(event.averageRating());
            company.setReviewCount(event.reviewCount());
            companyRepository.save(company);
        }
    }

    @Override
    public Company getById(Long id) {
        return findById(id);
    }

    @Override
    public boolean deleteById(Long id) {

        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(Long id, Company company) {
        Company toBeUpdated = findById(id);
        if(toBeUpdated != null) {
            toBeUpdated.setDescription(company.getDescription());
            toBeUpdated.setName(company.getName());
            companyRepository.save(toBeUpdated);
            return true;
        }
        return false;
    }
}
