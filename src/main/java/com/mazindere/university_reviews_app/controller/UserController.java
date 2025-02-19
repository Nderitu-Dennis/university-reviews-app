package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.enums.UserRole;
import com.mazindere.university_reviews_app.enums.UserType;
import com.mazindere.university_reviews_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Show registration form
    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        // Add user types for dropdown
        List<UserType> userTypes = Arrays.asList(UserType.STUDENT, UserType.ALUMNI, UserType.PROSPECTIVE);
        model.addAttribute("userTypes", userTypes);

        return "register"; // Thymeleaf template
    }

    // Handle form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setRole(UserRole.USER); // Assign default role
        userService.saveUser(user);
        return "index"; // Redirect with success message
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Show login.html
    }

    @GetMapping("/index")
    public String showIndexPage(){
        return "index";
    }


    }

