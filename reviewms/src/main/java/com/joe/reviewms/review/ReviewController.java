package com.joe.reviewms.review;

import com.joe.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

///companies/{companyId}/
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMessageProducer reviewMessageProducer;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReview(@RequestParam Long companyId) {
        List<Review> all = reviewService.getAllReview(companyId);
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getCompanyById(@PathVariable Long ReviewId) {
        Review review = reviewService.getReviewById(ReviewId);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        Boolean reviewAdded = reviewService.addReview(companyId, review);
        if (reviewAdded) {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("review added successfully", HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("review not added", HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review) {
        Boolean updateStatus = reviewService.updateReview(reviewId, review);


        if (updateStatus) {
            return new ResponseEntity<>("review is updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("review was not updated", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long reviewId
    ) {
        Boolean job = reviewService.deleteReviewById(reviewId);
        if (job) {
            return new ResponseEntity<>("review is deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("review was not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId){
        List<Review> reviewList =reviewService.getAllReview(companyId);

        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }

}
