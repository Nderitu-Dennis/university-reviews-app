package com.mazindere.university_reviews_app.service;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.repository.ReviewRepository;
import com.mazindere.university_reviews_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
   // private final ScrapingService scrapingService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
       // this.scrapingService = scrapingService;
    }

    public List<Review> getReviewsByUniversity(String universityName) {
        System.out.println("retrieving a review from the db for: " + universityName);
        List<Review> reviews = reviewRepository.findByReviewedUniversityOrderByIdDesc(universityName);

        if (!reviews.isEmpty()) {
            System.out.println(" ..reviews have been loaded to the view..");
        } else {
            System.out.println("No reviews found - checking if any reviews exist in database");
        }
        return reviews;
    }

    // ReviewService.java - Modify the saveReview method
    public Review saveReview(Review review, Long userId) {
        review.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        if (review.getCreatedAt() == null) {
            review.setCreatedAt(new Date());
        }

        System.out.println("****saving a review in the db...****");
        return reviewRepository.save(review);
        // Removed the scraping trigger since we'll scrape fresh on each view
    }

    public Review getReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    public Review updateReview(Long reviewId, String title, String reviewText, int rating) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setTitle(title);
        review.setReviewText(reviewText);
        review.setRating(rating);

        return reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
