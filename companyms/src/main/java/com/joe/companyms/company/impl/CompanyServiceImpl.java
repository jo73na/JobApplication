package com.joe.companyms.company.impl;

import com.joe.companyms.company.Company;
import com.joe.companyms.company.CompanyRepository;
import com.joe.companyms.company.CompanyService;
import com.joe.companyms.company.client.ReviewClient;
import com.joe.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    // private List<Job> jobs = new ArrayList<>();
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    ReviewClient reviewClient;

    @Override
    public List<Company> findAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteCompanyById(Long id) {
        try {
            companyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateCompany(Long id, Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setTitle(updatedCompany.getTitle());
            company.setDescription(updatedCompany.getDescription());
            // company.setJobs(updatedCompany.getJobs());
            companyRepository.save(company);
            return true;
        }

        return false;
    }

    @Override
    public void updateCompany(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getCompanyId()+"------> company id ");
        Company company=companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()->new NotFoundException("Company not found"));

        Double averageReviews = reviewClient.getAverageReviews(reviewMessage.getCompanyId());
        company.setRating(averageReviews);
        companyRepository.save(company);
        System.out.println(company.getRating()+"------> saved new rating");
    }
}
