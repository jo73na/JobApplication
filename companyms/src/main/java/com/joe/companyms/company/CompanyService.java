package com.joe.companyms.company;

import com.joe.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    public List<Company> findAllCompany();

    public void createCompany(Company company);

    Company getCompanyById(Long id);

    Boolean deleteCompanyById(Long id);

    Boolean updateCompany(Long id, Company company);

    void updateCompany(ReviewMessage reviewMessage);
}
