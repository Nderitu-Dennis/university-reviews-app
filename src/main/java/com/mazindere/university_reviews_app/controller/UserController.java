package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.enums.UserRole;
import com.mazindere.university_reviews_app.enums.UserType;
import com.mazindere.university_reviews_app.service.EmailService;
import com.mazindere.university_reviews_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show registration form
    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        // Add user types for dropdown
        List<UserType> userTypes = Arrays.asList(UserType.STUDENT, UserType.ALUMNI);
        model.addAttribute("userTypes", userTypes);

        return "register"; // Thymeleaf template
    }

    // Handle initial registration and send verification email
    @PostMapping("/register")
    public String registerInitial(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        // Check if email is institutional
        if (!isValidStudentEmail(user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Only students with a university email can register.");
            return "redirect:/registration-form";
        }

        // Check if email already exists
        if (userService.emailExists(user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email already exists!");
            return "redirect:/registration-form";
        }

        // Generate verification token
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);

        // Set as unverified
        user.setEmailVerified(false);

        // Assign default role
        user.setRole(UserRole.USER);

        // Save user (but they can't login until verified)
        userService.saveUser(user, true);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), token);

        redirectAttributes.addFlashAttribute("success", "Registration started! Please check your email to verify your account.");
        return "registration-confirmation";
    }

    // Handle the verification link click
    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        // Find user by token
        System.out.println("Verification attempt with token: " + token);

        User user = userService.findByVerificationToken(token);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired verification token.");
            return "redirect:/login";
        }
        System.out.println("Found user: " + user.getEmail() + ", current verification status: " + user.isEmailVerified());


        // Mark as verified
        user.setEmailVerified(true);

        // Clear the token (optional, for security)
        user.setVerificationToken(null);

        // Save the updated user
        userService.saveUser(user, false);
        System.out.println("User " + user.getEmail() + " marked as verified and saved");


        redirectAttributes.addFlashAttribute("success", "Email verified successfully! You can now log in.");
        return "redirect:/login";
    }

    private boolean isValidStudentEmail(String email) {
        return email != null && (email.endsWith("@mmu.ac.ke")
                || email.endsWith("@uonbi.ac.ke")
                || email.endsWith("@chuka.ac.ke")
                || email.endsWith("@students.must.ac.ke"));
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
            model.addAttribute("success", success);
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