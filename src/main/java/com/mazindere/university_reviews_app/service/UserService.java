package com.mazindere.university_reviews_app.service;

import com.mazindere.university_reviews_app.entity.User;
import com.mazindere.university_reviews_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
