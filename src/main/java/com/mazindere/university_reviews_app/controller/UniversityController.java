package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.model.University;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UniversityController {

    private static final Map<String, University> universityData = new HashMap<>();

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
                "https://maps.app.goo.gl/c9ouG9JcmjXBhLiCA"
        ));

        universityData.put("ku", new University(
                "Kenyatta University",
                "ku-hero.jpg",
                "A leader in business, education, and health sciences.",
                Arrays.asList("Business Administration", "Education", "Medicine"),
                "Kenyatta University is among the largest universities in Kenya.",
                "https://www.ku.ac.ke",
                "https://maps.google.com?q=Kenyatta+University"
        ));
    }

    @GetMapping("/universities/{uniName}")
    public String getUniversity(@PathVariable String uniName, Model model) {
        University university = universityData.get(uniName);
        if (university == null) {
            return "error-page"; // Handle unknown universities
        }
        model.addAttribute("university", university);
        return "university"; // Loads the base template dynamically
    }
}
