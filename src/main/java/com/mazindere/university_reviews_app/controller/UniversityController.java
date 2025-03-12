package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.model.University;
import com.mazindere.university_reviews_app.service.ReviewService;
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

    public UniversityController(ReviewService reviewService){
        this.reviewService=reviewService;
    }

    // Sample university data
    static {
        universityData.put("mmu", new University(
                "Multimedia University of Kenya",
                "mmu-hero.jpeg",
                "Multimedia University of Kenya (MMU) is a top institution in media, ICT," +
                        " engineering, and business studies, offering industry-focused programs and state-of-the-art " +
                        "facilities. Located in Nairobi along Magadi Road & near Ongata Rongai town, MMU provides hands-on training in broadcasting," +
                        " film production, software development and engineering fields like telecommunications " +
                        "and electrical engineering. With student led clubs, media stations, and strong industry links, students gain" +
                        " real-world experience through internships and career-driven education. If you're looking for a practical and " +
                        "dynamic learning environment, MMU is the place to be!",

                Arrays.asList("Faculty of Business and Economics",
                        "Faculty of Computing & Information Technology",
                        "Faculty of Engineering & Technology",
                        "Faculty of Media & Communication",
                        "Faculty of Science & Technology",
                        "Faculty of Social Sciences & Technology",
                        "National Institute for Optics & Lasers",
                        "MMU TVET Centre",
                        "Board of Post graduate Studies"),

                "Multimedia University has produced top-tier engineers and media professionals in Kenya.",
                "https://www.mmu.ac.ke",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.658655124362!2d36.76818919999999!3d-1.3814587!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f059a7c019c03%3A0x8092af97623fe89b!2sJQ99%2B98R%2C%20Nairobi!5e0!3m2!1sen!2ske!4v1740737475229!5m2!1sen!2ske"
        ));

        universityData.put("ku", new University(
                "Kenyatta University",
                "ku-hero.jpg",
                "A leader in business, education, and health sciences.",
                Arrays.asList("Business Administration", "Education", "Medicine"),
                "Kenyatta University is among the largest universities in Kenya.",
                "https://www.ku.ac.ke",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.969228824202!2d36.93409500959767!3d-1.182077698801638!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f3ff0f27b27f5%3A0xb0e3964cef8200a0!2sKenyatta%20University%2C%20Main%20Campus!5e0!3m2!1sen!2ske!4v1740806593380!5m2!1sen!2ske"
        ));
    }

    @GetMapping("/universities/{uniName}")
    public String getUniversity(@PathVariable String uniName, Model model) {
        University university = universityData.get(uniName);
        if (university == null) {
            return "error-page"; // Handle unknown universities
        }

        // Fetch reviews for this university
        List<Review> reviews = reviewService.getReviewsByUniversity(uniName);
        model.addAttribute("reviews", reviews); // Pass reviews to the Thymeleaf template
        model.addAttribute("university", university);
        model.addAttribute("university", university);
        return "university"; // Loads the base template dynamically
    }
}
