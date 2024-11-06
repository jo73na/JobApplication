package com.joe.companyms.company.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "reviewms")
public interface ReviewClient {
    @GetMapping("/reviews/averageRating")
    Double getAverageReviews(@RequestParam("companyId") Long companyId);
}