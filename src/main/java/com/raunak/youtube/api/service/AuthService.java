package com.raunak.youtube.api.service;

import com.raunak.youtube.api.model.User;
import com.raunak.youtube.api.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =====================================================
    // REGISTER USER
    // =====================================================

    public User registerUser(
            String username,
            String email,
            String password) {

        // Check if username already exists
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt password using BCrypt
        String encodedPassword =
                passwordEncoder.encode(password);

        // Create new user
        User user = new User(
                username,
                email,
                encodedPassword
        );

        // Save user to MySQL
        return userRepository.save(user);
    }


// =====================================================
// LOGIN USER
// =====================================================

public User loginUser(
        String username,
        String password) {

    // Find user by username
    User user = userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException("Invalid username or password"));

    // Compare entered password with BCrypt password
    if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new RuntimeException("Invalid username or password");
    }

    // Login successful
    return user;
}
}