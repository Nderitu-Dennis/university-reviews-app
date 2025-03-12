package com.mazindere.university_reviews_app.service;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.repository.ReviewRepository;
import com.mazindere.university_reviews_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
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

    public Review saveReview(Review review, Long userId) {
        review.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        if (review.getCreatedAt() == null) {
            review.setCreatedAt(new Date()); // Ensure createdAt is set
        }

        System.out.println("****saving a review in the db...****");
        return reviewRepository.save(review);
    }

}
