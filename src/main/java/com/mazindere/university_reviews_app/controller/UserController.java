package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.enums.UserRole;
import com.mazindere.university_reviews_app.enums.UserType;
import com.mazindere.university_reviews_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.net.URL;
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
                                @RequestParam(value = "success", required = false) String success,
                                @RequestParam(value = "redirectUrl", required = false) String redirectUrl,
                                HttpServletRequest request){

        // If no explicit redirectUrl is provided, use the referer header
        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = request.getHeader("Referer");
        }

        if (error != null) {
            model.addAttribute("error", "Invalid email or password!");
        }
        if (success != null) {
            model.addAttribute("success", "success");
        }

        // Ensure the redirectUrl is a relative path and starts with a '/'
        if (redirectUrl != null && redirectUrl.startsWith("http")) {
            try {
                URL url = new URL(redirectUrl);
                redirectUrl = url.getPath();
            } catch (MalformedURLException e) {
                redirectUrl = "/index"; // fallback to default
            }
        }

        // Only store redirectUrl if it starts with "/universities/"
        if (redirectUrl == null || !redirectUrl.startsWith("/universities/")) {
            redirectUrl = "/index"; // Default redirect for non-university pages
        }

        model.addAttribute("redirectUrl", redirectUrl); // Store the redirect URL

        return "login";
    }

    @GetMapping("/index")
    public String showIndexPage(){
        return "index";
    }




    }

