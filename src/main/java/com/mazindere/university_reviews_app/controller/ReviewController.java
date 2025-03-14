package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.model.University;
import com.mazindere.university_reviews_app.service.ReviewService;
import com.mazindere.university_reviews_app.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;


    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    // Submit a review
    @PostMapping("/submit")
    public String submitReview(@RequestParam String reviewedUniversity,
                               @RequestParam String title,
                               @RequestParam Integer rating,
                               @RequestParam String reviewText,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        User user = userService.findByEmail(userDetails.getUsername());

        Review review = new Review();
        review.setReviewedUniversity(reviewedUniversity);
        review.setTitle(title);
        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setUser(user);
        review.setReviewerName(user.getName());

        reviewService.saveReview(review, user.getId());

        //fetch reviews again
        List<Review> reviews = reviewService.getReviewsByUniversity(reviewedUniversity);
        model.addAttribute("reviews", reviews);

        // Load the university again
        University university = UniversityController.universityData.get(reviewedUniversity);
        model.addAttribute("university", university);

        return "redirect:/display-reviews/" + reviewedUniversity;   }

    //edit a review
    @PostMapping("/edit")
    public String editReview(@RequestParam Long reviewId,
                             @RequestParam String title,
                             @RequestParam String reviewText,
                             @RequestParam int rating,
                             @RequestParam String reviewedUniversity,
                             Model model) {

        reviewService.updateReview(reviewId, title, reviewText, rating);

        // Load the university and add it to the model
        University university = UniversityController.universityData.get(reviewedUniversity);
        model.addAttribute("university", university);

        return "redirect:/display-reviews/" + reviewedUniversity;
    }

    //delete a review
    @PostMapping("/delete")
    public String deleteReview(@RequestParam Long reviewId,
                               @RequestParam String reviewedUniversity,
                               Model model) {
        reviewService.deleteReview(reviewId);

        //  Load the university and add it to the model
        University university = UniversityController.universityData.get(reviewedUniversity);
        model.addAttribute("university", university);

        return "redirect:/display-reviews/" + reviewedUniversity;
    }



}

