package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.model.University;
import com.mazindere.university_reviews_app.service.ReviewService;
import com.mazindere.university_reviews_app.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping("/submit")
    public String submitReview(@RequestParam String reviewedUniversity,
                               @RequestParam String title,
                               @RequestParam Integer rating,
                               @RequestParam String reviewText,
                               @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(userDetails.getUsername());
        Review review = new Review();
        review.setReviewedUniversity(reviewedUniversity);
        review.setTitle(title);
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setUser(user);
        review.setReviewerName(user.getName());

        reviewService.saveReview(review, user.getId());

        return "redirect:/display-reviews/" + reviewedUniversity;
    }

    @PostMapping("/edit")
    public String editReview(@RequestParam Long reviewId,
                             @RequestParam String title,
                             @RequestParam String reviewText,
                             @RequestParam int rating,
                             @RequestParam String reviewedUniversity,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Review review = reviewService.getReviewById(reviewId);
        if (review == null || !review.getUser().getEmail().equals(userDetails.getUsername())) {
            return "redirect:/display-reviews/" + reviewedUniversity;
        }

        reviewService.updateReview(reviewId, title, reviewText, rating);
        return "redirect:/display-reviews/" + reviewedUniversity;
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam Long reviewId,
                               @RequestParam String reviewedUniversity,
                               @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Review review = reviewService.getReviewById(reviewId);
        if (review == null || !review.getUser().getEmail().equals(userDetails.getUsername())) {
            return "redirect:/display-reviews/" + reviewedUniversity;
        }

        reviewService.deleteReview(reviewId);
        return "redirect:/display-reviews/" + reviewedUniversity;
    }
}
