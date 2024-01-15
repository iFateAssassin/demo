package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        try {
            // Encode the password before saving to the database
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Set the enabled status as needed
            user.setEnabled(true); // or set based on your logic

            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            // Add proper logging in a real application
            throw new RuntimeException("Error saving user to the database");
        }
    }
}
