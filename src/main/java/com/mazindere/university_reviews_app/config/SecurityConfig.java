package com.mazindere.university_reviews_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.web.util.UrlUtils.isValidRedirectUrl;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/registration-form", "/register", "/login", "/universities/**").permitAll() // Public pages
                        .requestMatchers("/reviews/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Admin-only pages
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/funfact/**").permitAll()
                        .anyRequest().authenticated() // Secure all other pages
                )
                .formLogin(login -> login
                        .loginPage("/login") // Custom login page
                        .successHandler(customAuthenticationSuccessHandler()) // Redirect to stored URL
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            String redirectUrl = request.getParameter("redirectUrl");

            // Validate and sanitize redirectUrl
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                // Ensure the URL is relative and safe
                if (!redirectUrl.startsWith("/")) {
                    redirectUrl = "/" + redirectUrl;
                }

                // Optional: Add additional validation to prevent open redirects
                if (isValidRedirectUrl(redirectUrl)) {
                    response.sendRedirect(redirectUrl);
                    return;
                }
            }

            // Fallback to default page
            response.sendRedirect("/index");
        };
    }
}
