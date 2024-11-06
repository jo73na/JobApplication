package com.joe.jobms.job.impl;

import com.joe.jobms.job.Job;
import com.joe.jobms.job.JobRepository;
import com.joe.jobms.job.JobService;
import com.joe.jobms.job.clients.CompnayClient;
import com.joe.jobms.job.clients.ReviewClient;
import com.joe.jobms.job.dto.JobDTO;
import com.joe.jobms.job.external.Company;
import com.joe.jobms.job.external.Review;
import com.joe.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    // private List<Job> jobs = new ArrayList<>();
    @Autowired
    private JobRepository jobRepository;
    // private Long nextId = 1L;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CompnayClient compnayClient;
    @Autowired
    private ReviewClient reviewsClient;

int attempt= 0;
    @Override
    @RateLimiter(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    @Retry(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt:----->>> "+ ++attempt);
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public List<String > companyBreakerFallback(){
        List<String > lists= new ArrayList<>();
        lists.add("Dummy");
        return lists;
    }

    private JobDTO convertDTO(Job job) {
        //RestTemplate restTemplate = new RestTemplate();

        Long companyId = job.getCompanyId();
        System.out.println("check this value: ->>>>" + companyId);
        Company company = compnayClient.getCompany(companyId);


        System.out.println("check this value: ->>>>" + company.getTitle());
        List<Review> reviews = reviewsClient.getReviews(companyId);

//restTemplate.getForObject("http://COMPANYMS:8081/companies/"+companyId, Company.class);
//                restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId="+companyId, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
        //   List < Review > reviews = reviewResponse.getBody();

        JobDTO jobDTO = JobMapper.mapToJobWithCompany(job, company, reviews);
        return jobDTO;

    }


    @Override
    public void createJob(Job job) {
        //    job.setId(nextId++);
        jobRepository.save(job);

    }

    @Override
    public JobDTO getJobById(Long id) {
//        Optional<Job> result = jobs.stream()
//                .filter(job -> job.getId().equals(id))
//                .findFirst();
//        for (Job job : jobs) {
//            if (job.getId().equals(id)) {
//                return job;
//            }
        Job job = jobRepository.findById(id).orElse(null);
        return convertDTO(job);


    }

    @Override
    public Boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }

        return false;
    }
}
