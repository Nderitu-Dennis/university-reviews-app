package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.enums.UserRole;
import com.mazindere.university_reviews_app.enums.UserType;
import com.mazindere.university_reviews_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (userService.emailExists(user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email already exists!"); // Pass error message
            return "redirect:/registration-form"; // Redirects back to the registration page
        }

        // Assign default role before saving
        user.setRole(UserRole.USER);
        userService.saveUser(user);

        // Store success message using FlashAttributes
        redirectAttributes.addFlashAttribute("success", "Registration successful! You can now log in.");

        return "redirect:/login"; // Redirect to login after successful registration
    }


    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "success", required = false) String success) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password!");
        }
        if (success != null) {
            model.addAttribute("success", "success");
        }
        return "login";
    }

    @GetMapping("/index")
    public String showIndexPage(){
        return "index";
    }




    }

