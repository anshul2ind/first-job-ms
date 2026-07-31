package com.project.companyms;

import com.project.companyms.event.CompanyRatingUpdatedEvent;

import java.util.List;

public interface CompanyService {
    public List<Company> findAll();
    public String create(Company company);
    public void updateRating(CompanyRatingUpdatedEvent event);
    public Company getById(Long id);
    public boolean deleteById(Long id);
    public boolean updateById(Long id, Company company);
}
