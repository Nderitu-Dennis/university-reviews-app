package com.mazindere.university_reviews_app.service;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.repository.ReviewRepository;
import com.mazindere.university_reviews_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    //fetch reviews
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

    //save a review
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
        return review.orElse(null); // Return review if found, otherwise return null
    }

    //edit a review
    public Review updateReview(Long reviewId, String title, String reviewText, int rating) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setTitle(title);
        review.setReviewText(reviewText);
        review.setRating(rating);

        return reviewRepository.save(review);
    }

    //delete a review
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
