package com.raunak.youtube.api.service;

import com.raunak.youtube.api.model.User;
import com.raunak.youtube.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class PasswordResetService {

    private static final Logger logger =
            LoggerFactory.getLogger(PasswordResetService.class);

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private static final int RESET_TOKEN_BYTES = 32;
    private static final int RESET_TOKEN_LIFETIME_MINUTES = 30;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final String fromAddress;
    private final String publicBaseUrl;

    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordResetService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ObjectProvider<JavaMailSender> mailSenderProvider,
            @Value("${app.mail.from:${spring.mail.username:}}")
            String fromAddress,
            @Value("${app.public-base-url:http://localhost:8080}")
            String publicBaseUrl) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderProvider = mailSenderProvider;
        this.fromAddress = fromAddress;
        this.publicBaseUrl = publicBaseUrl;
    }

    @Transactional
    public void requestPasswordReset(String email) {

        if (email == null || email.isBlank()
                || email.trim().length() > 254
                || !EMAIL_PATTERN.matcher(email.trim()).matches()) {

            throw new IllegalArgumentException(
                    "Enter a valid email address.");
        }

        Optional<User> matchingUser =
                userRepository.findByEmail(email.trim());

        // Do not reveal whether an email address has an account.
        if (matchingUser.isEmpty()) {
            return;
        }

        JavaMailSender mailSender =
                mailSenderProvider.getIfAvailable();

        if (mailSender == null
                || fromAddress == null
                || fromAddress.isBlank()) {

            throw new IllegalStateException(
                    "Password recovery email is not configured.");
        }

        User user = matchingUser.get();

        // Generate a cryptographically secure, 256-bit reset token.
        byte[] tokenBytes = new byte[RESET_TOKEN_BYTES];
        secureRandom.nextBytes(tokenBytes);

        String token = HexFormat.of().formatHex(tokenBytes);

        // Store only the SHA-256 hash in the database.
        user.setPasswordResetTokenHash(hashToken(token));

        user.setPasswordResetExpiresAt(
                LocalDateTime.now().plusMinutes(
                        RESET_TOKEN_LIFETIME_MINUTES));

        userRepository.save(user);

        // Ensure this path matches your actual reset-password HTML page.
        String resetUrl = publicBaseUrl.replaceAll("/+$", "")
                + "/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromAddress);
        message.setTo(user.getEmail());
        message.setSubject("Reset your RTube password");

        message.setText(
                "Hello " + user.getUsername() + ",\n\n"
                + "We received a request to reset your RTube password.\n\n"
                + "Use the link below to choose a new password:\n\n"
                + resetUrl + "\n\n"
                + "This link expires in "
                + RESET_TOKEN_LIFETIME_MINUTES
                + " minutes and can only be used once.\n\n"
                + "If you did not request a password reset, "
                + "you can ignore this email. Your current password "
                + "will remain unchanged.\n\n"
                + "Best regards,\n"
                + "RTube Team");

        try {
            mailSender.send(message);

        } catch (MailException exception) {

            // Clear the token if the email cannot be sent.
            user.setPasswordResetTokenHash(null);
            user.setPasswordResetExpiresAt(null);
            userRepository.save(user);

            logger.error(
                    "Unable to send password reset email for user ID {}.",
                    user.getId(),
                    exception);

            throw new IllegalStateException(
                    "Unable to send the password recovery email right now.");
        }
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {

        if (token == null
                || !token.matches("[a-fA-F0-9]{64}")) {

            throw new IllegalArgumentException(
                    "This password reset link is invalid or has expired.");
        }

        if (newPassword == null
                || newPassword.length() < 8
                || newPassword.getBytes(StandardCharsets.UTF_8).length > 72) {

            throw new IllegalArgumentException(
                    "Password must be at least 8 characters "
                    + "and no more than 72 bytes.");
        }

        String tokenHash = hashToken(token);

        User user = userRepository
                .findByPasswordResetTokenHash(tokenHash)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "This password reset link is invalid "
                                + "or has expired."));

        LocalDateTime expiry = user.getPasswordResetExpiresAt();

        if (expiry == null
                || !expiry.isAfter(LocalDateTime.now())) {

            throw new IllegalArgumentException(
                    "This password reset link is invalid or has expired.");
        }

        // Update the password using the existing BCrypt encoder.
        user.setPassword(passwordEncoder.encode(newPassword));

        // Make the reset link single-use.
        user.setPasswordResetTokenHash(null);
        user.setPasswordResetExpiresAt(null);

        userRepository.save(user);
    }

    private String hashToken(String token) {

        try {
            byte[] hash = MessageDigest
                    .getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {

            throw new IllegalStateException(
                    "SHA-256 is not available.",
                    exception);
        }
    }
}