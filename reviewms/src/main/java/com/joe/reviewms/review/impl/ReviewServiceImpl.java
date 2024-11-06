package com.joe.reviewms.review.impl;


import com.joe.reviewms.review.Review;
import com.joe.reviewms.review.ReviewRepository;
import com.joe.reviewms.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    // private List<Job> jobs = new ArrayList<>();
    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<Review> getAllReview(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Boolean addReview(Long companyId, Review review) {

        if (companyId != null && review!=null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    public Review getReviewById( Long reviewId) {
//        List<Review> reviews = getAllReview(companyId);
//        return reviews.stream().filter(review -> review.getId().equals(reviewId)).findFirst().orElse(null);
return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Boolean deleteReviewById( Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review!= null) {
            Review reviewById = reviewRepository.findById(reviewId).orElse(null);

            reviewRepository.delete(review);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public Boolean updateReview( Long reviewId, Review updatedReview) {

Review review = reviewRepository.findById(reviewId).orElse(null);
        if (reviewId != null) {
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());
            review.setCompanyId(updatedReview.getCompanyId());
            reviewRepository.save(updatedReview);
            return true;
        } else {
            return false;
        }


    }
}
