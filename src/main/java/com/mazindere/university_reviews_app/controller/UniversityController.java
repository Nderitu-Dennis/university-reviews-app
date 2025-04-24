package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.model.University;
import com.mazindere.university_reviews_app.service.ReviewService;
import com.mazindere.university_reviews_app.service.ScrapingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UniversityController {

    public static final Map<String, University> universityData = new HashMap<>();
    private final ReviewService reviewService;
    private final ScrapingService scrapingService;

    public UniversityController(ReviewService reviewService, ScrapingService scrapingService){
        this.reviewService=reviewService;
        this.scrapingService = scrapingService;
    }

    // university data
    static {
        //mmu
        universityData.put("mmu", new University(
                "Multimedia University of Kenya",
                "mmu-hero.jpeg"
                ));


        //ku
        universityData.put("ku", new University(
                "Kenyatta University",
                "ku-hero.jpg"
                ));

        //uon
        universityData.put("uon",new University(
                "University of Nairobi",
                "uon-hero.png"
                ));

        //tuk
        universityData.put("tuk", new University(
                "Technical University of Kenya",
                "tuk-hero.png"
                ));

        //chuka
        universityData.put("chuka", new University(
                "Chuka University",
                "chuka-hero.jpg"
                ));


        //egerton
        universityData.put("egerton", new University(
                "Egerton University",
                "egerton-hero.jpg"
                ));


        //jkuat
        universityData.put("jkuat", new University(
                "JKUAT",
                "jkuat-hero.png"
                ));



        //maseno
        universityData.put("maseno", new University(
                "Maseno University",
                "maseno-hero.jpg"
                ));



        //meru
        universityData.put("meru", new University(
                "Meru University",
                "meru-hero.jpg"
                ));

        //kisii
        universityData.put("kisii", new University(
                "Kisii University",
                "kisii-hero.png"
                ));

                  }

    @GetMapping("/universities/{uniName}")
    public String getUniversity(@PathVariable String uniName, Model model, HttpServletRequest request) {
        model.addAttribute("currentRequestUri", request.getRequestURI());
        University university = universityData.get(uniName);
        if (university == null) {
            return "error-page"; // Handle unknown universities
        }

        // Fetch reviews for this university
        List<Review> reviews = reviewService.getReviewsByUniversity(uniName);
        model.addAttribute("reviews", reviews); // Pass reviews to the Thymeleaf template
        model.addAttribute("university", university);

        // Get program information from scraping
        List<String> programs = ScrapingService.scrapeUniversityPrograms(uniName);
        model.addAttribute("programs", programs);
        model.addAttribute("programsUrl", ScrapingService.getProgramUrl(uniName));

        return "university"; // Loads the base template dynamically
    }
}
