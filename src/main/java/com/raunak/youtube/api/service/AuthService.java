package com.raunak.youtube.api.service;
import jakarta.servlet.http.HttpServletRequest;
import com.raunak.youtube.api.model.User;
import com.raunak.youtube.api.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Service
public class AuthService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final long MAX_PROFILE_PICTURE_BYTES = 5 * 1024 * 1024;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
private final LoginNotificationService loginNotificationService;
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
 LoginNotificationService loginNotificationService
) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
this.loginNotificationService = loginNotificationService;
    }

    // =====================================================
    // REGISTER USER
    // =====================================================

    public User registerUser(
            String username,
            String email,
            String password) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (username.trim().length() > 80) {
            throw new IllegalArgumentException("Username must be 80 characters or fewer.");
        }
        if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        if (email.trim().length() > 254) {
            throw new IllegalArgumentException("Email must be 254 characters or fewer.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (password.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new IllegalArgumentException("Password must be 72 bytes or fewer.");
        }

        String normalizedUsername = username.trim();
        String normalizedEmail = email.trim();

        // Check if username already exists
        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new IllegalArgumentException("That username is already in use.");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("That email is already in use.");
        }

        // Encrypt password using BCrypt
        String encodedPassword =
                passwordEncoder.encode(password);

        // Create new user
        User user = new User(
                normalizedUsername,
                normalizedEmail,
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
        String password,
        HttpServletRequest request) {

    // Find the registered user.
    User user = userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Invalid username or password"));

    // Check the entered password.
    if (password == null
            || !passwordEncoder.matches(
                    password, user.getPassword())) {

        // Notify the registered email about the failed attempt.
        loginNotificationService.sendFailedLoginAlert(
                user, request);

        // Reject the login.
        throw new RuntimeException(
                "Invalid username or password");
    }

    // Login successful.
    return user;
}

    @Transactional
    public User updateUserProfile(
            int id,
            String currentPassword,
            String username,
            String email,
            MultipartFile profilePicture,
            String newPassword,
            String confirmNewPassword) throws IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (currentPassword == null
                || !passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new SecurityException("Current password is incorrect.");
        }

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (username.length() > 80) {
            throw new IllegalArgumentException("Username must be 80 characters or fewer.");
        }
        if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        if (email.length() > 254) {
            throw new IllegalArgumentException("Email must be 254 characters or fewer.");
        }

        boolean changingPassword = newPassword != null && !newPassword.isEmpty();
        boolean confirmingPassword =
                confirmNewPassword != null && !confirmNewPassword.isEmpty();
        if (changingPassword || confirmingPassword) {
            if (!changingPassword || !confirmingPassword) {
                throw new IllegalArgumentException(
                        "Enter and confirm your new password to change it.");
            }
            if (!newPassword.equals(confirmNewPassword)) {
                throw new IllegalArgumentException("The new passwords do not match.");
            }
            if (newPassword.length() < 8) {
                throw new IllegalArgumentException(
                        "New password must be at least 8 characters long.");
            }
            if (newPassword.getBytes(StandardCharsets.UTF_8).length > 72) {
                throw new IllegalArgumentException(
                        "New password must be 72 bytes or fewer.");
            }
        }

        String normalizedUsername = username.trim();
        String normalizedEmail = email.trim();

        if (userRepository.existsByUsernameAndIdNot(normalizedUsername, id)) {
            throw new IllegalArgumentException("That username is already in use.");
        }
        if (userRepository.existsByEmailAndIdNot(normalizedEmail, id)) {
            throw new IllegalArgumentException("That email is already in use.");
        }

        user.setUsername(normalizedUsername);
        user.setEmail(normalizedEmail);
        if (changingPassword) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        if (profilePicture != null && !profilePicture.isEmpty()) {
            if (profilePicture.getSize() > MAX_PROFILE_PICTURE_BYTES) {
                throw new IllegalArgumentException("Profile pictures must be 5 MB or smaller.");
            }

            String contentType = detectImageContentType(profilePicture.getBytes());
            if (contentType == null) {
                throw new IllegalArgumentException(
                        "Choose a valid JPEG, PNG, GIF, or WebP image.");
            }

            user.setProfilePicture(profilePicture.getBytes());
            user.setProfilePictureContentType(contentType);
        }

        return userRepository.save(user);
    }

    public User getUserProfile(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    private String detectImageContentType(byte[] image) {
        if (image.length >= 8
                && (image[0] & 0xff) == 0x89
                && image[1] == 0x50
                && image[2] == 0x4e
                && image[3] == 0x47
                && image[4] == 0x0d
                && image[5] == 0x0a
                && image[6] == 0x1a
                && image[7] == 0x0a) {
            return "image/png";
        }
        if (image.length >= 3
                && (image[0] & 0xff) == 0xff
                && (image[1] & 0xff) == 0xd8
                && (image[2] & 0xff) == 0xff) {
            return "image/jpeg";
        }
        if (image.length >= 6
                && image[0] == 'G'
                && image[1] == 'I'
                && image[2] == 'F'
                && image[3] == '8'
                && (image[4] == '7' || image[4] == '9')
                && image[5] == 'a') {
            return "image/gif";
        }
        if (image.length >= 12
                && image[0] == 'R'
                && image[1] == 'I'
                && image[2] == 'F'
                && image[3] == 'F'
                && image[8] == 'W'
                && image[9] == 'E'
                && image[10] == 'B'
                && image[11] == 'P') {
            return "image/webp";
        }
        return null;
    }
}