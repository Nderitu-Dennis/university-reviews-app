package com.mazindere.university_reviews_app.service;

import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.isEmailVerified()) { // unverified users cant log in
            throw new BadCredentialsException("Email not verified.");
        }

        return user;
    }


    public User saveUser(User user, boolean encodePassword) {
        // Hash password before saving
        if(encodePassword){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    //  Check if email already exists
    public boolean emailExists(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByVerificationToken(String token){
        return userRepository.findByVerificationToken(token).orElse(null);
    }

}
