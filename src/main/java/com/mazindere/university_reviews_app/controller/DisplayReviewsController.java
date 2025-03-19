package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.model.University;
import com.mazindere.university_reviews_app.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.mazindere.university_reviews_app.controller.UniversityController.universityData;

@Controller
@RequestMapping("/display-reviews")
public class DisplayReviewsController {

    private final ReviewService reviewService;

    public DisplayReviewsController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{universityName}")
    public String displayReviews(@PathVariable String universityName, Model model) {

        String normalizedName = universityName.trim();
        List<Review> reviews = reviewService.getReviewsByUniversity(normalizedName);
        University university = universityData.get(normalizedName);

        model.addAttribute("uniName", normalizedName);
        model.addAttribute("reviews", reviews);
        model.addAttribute("university", university);

        return "university";  //  template that displays reviews
    }
}