package com.joe.reviewms.review;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public List<Review> getAllReview(Long companyId);

    public Boolean addReview(Long companyId,Review review);

    Review getReviewById(Long reviewId);

    Boolean deleteReviewById(Long reviewId);

    Boolean updateReview(Long reviewId, Review review);
}
