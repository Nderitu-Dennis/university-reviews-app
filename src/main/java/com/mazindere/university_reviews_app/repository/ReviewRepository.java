package com.mazindere.university_reviews_app.repository;

import com.mazindere.university_reviews_app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewedUniversityOrderByIdDesc(String reviewedUniversity); // Fetch reviews for a specific university

}
