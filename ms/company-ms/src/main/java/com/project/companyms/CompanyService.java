package com.project.companyms;

import java.util.List;

public interface CompanyService {
    public List<Company> findAll();
    public String create(Company company);
    public Company getById(Long id);
    public boolean deleteById(Long id);
    public boolean updateById(Long id, Company company);
}
