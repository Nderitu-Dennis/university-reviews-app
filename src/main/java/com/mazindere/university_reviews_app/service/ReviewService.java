package com.mazindere.university_reviews_app.service;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.repository.ReviewRepository;
import com.mazindere.university_reviews_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
   // private final ScrapingService scrapingService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    // Fetch reviews for a specific university
    public List<Review> getReviewsByUniversity(String universityName) {
        System.out.println("retrieving reviews from the DB for: " + universityName);
        List<Review> reviews = reviewRepository.findByReviewedUniversityOrderByIdDesc(universityName);
        return reviews;
    }

    // Calculate the rating distribution (number of reviews for each rating 1-5)
    public Map<Integer, Long> getRatingDistribution(List<Review> reviews) {
        Map<Integer, Long> distribution = reviews.stream()
                .collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));

        // Ensure all ratings 1-5 are present in the map
        for (int i = 1; i <= 5; i++) {
            distribution.putIfAbsent(i, 0L);
        }

        return distribution;
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
