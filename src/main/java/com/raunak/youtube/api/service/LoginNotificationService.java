
package com.raunak.youtube.api.service;

import com.raunak.youtube.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class LoginNotificationService {

    private static final Logger logger =
            LoggerFactory.getLogger(LoginNotificationService.class);

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern(
                    "dd MMM yyyy, hh:mm:ss a z",
                    Locale.ENGLISH);

    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final String fromAddress;

    public LoginNotificationService(
            ObjectProvider<JavaMailSender> mailSenderProvider,
            @Value("${app.mail.from:${spring.mail.username:}}")
            String fromAddress) {
        this.mailSenderProvider = mailSenderProvider;
        this.fromAddress = fromAddress;
    }

    public void sendLoginAlert(
            User user,
            HttpServletRequest request) {

        if (user == null || user.getEmail() == null
                || user.getEmail().isBlank()) {
            logger.warn("Login alert skipped: user email is missing.");
            return;
        }

        JavaMailSender mailSender =
                mailSenderProvider.getIfAvailable();

        if (mailSender == null) {
            logger.warn(
                    "Login alert email was not sent: SMTP is not configured.");
            return;
        }

        if (fromAddress == null || fromAddress.isBlank()) {
            logger.warn(
                    "Login alert email was not sent: app.mail.from is not configured.");
            return;
        }

        String loginTime = ZonedDateTime.now()
                .format(TIME_FORMATTER);

        String userAgent = request.getHeader("User-Agent");

        if (userAgent == null || userAgent.isBlank()) {
            userAgent = "Not available";
        } else {
            // Limit the length of untrusted browser information.
            userAgent = userAgent
                    .replaceAll("[\\r\\n\\p{Cntrl}]", " ")
                    .trim();

            if (userAgent.length() > 500) {
                userAgent = userAgent.substring(0, 500);
            }
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(user.getEmail());
        message.setSubject("New sign-in to your RTube account");

        message.setText(
                "Hello " + user.getUsername() + ",\n\n"
                + "A new sign-in to your RTube account was just completed.\n\n"
                + "Login date and time: " + loginTime + "\n"
                + "Browser/device information: " + userAgent + "\n\n"
                + "If this wasn't you, please change your password.");

        try {
            mailSender.send(message);
            logger.info(
                    "Login alert email sent for user ID {}.",
                    user.getId());
        } catch (MailException exception) {
            // An email failure must not undo a successful login.
            logger.error(
                    "Unable to send login alert email for user ID {}.",
                    user.getId(),
                    exception);
        }
    }

public void sendFailedLoginAlert(
        User user,
        HttpServletRequest request) {

    if (user == null || user.getEmail() == null
            || user.getEmail().isBlank()) {
        logger.warn("Failed-login alert skipped: user email is missing.");
        return;
    }

    JavaMailSender mailSender =
            mailSenderProvider.getIfAvailable();

    if (mailSender == null
            || fromAddress == null
            || fromAddress.isBlank()) {
        logger.warn("Failed-login alert skipped: SMTP is not configured.");
        return;
    }

    String attemptTime = ZonedDateTime.now()
            .format(TIME_FORMATTER);

    String userAgent = request.getHeader("User-Agent");

    if (userAgent == null || userAgent.isBlank()) {
        userAgent = "Not available";
    } else {
        userAgent = userAgent
                .replaceAll("[\\r\\n\\p{Cntrl}]", " ")
                .trim();

        if (userAgent.length() > 500) {
            userAgent = userAgent.substring(0, 500);
        }
    }

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromAddress);
    message.setTo(user.getEmail());
    message.setSubject(
            "Security alert: unsuccessful sign-in to your RTube account");

    message.setText(
            "Hello " + user.getUsername() + ",\n\n"
            + "An unsuccessful sign-in attempt was made "
            + "using your RTube username.\n\n"
            + "Attempt date and time: " + attemptTime + "\n"
            + "Browser/device information: " + userAgent + "\n\n"
            + "If this wasn't you, make sure your password is secure "
            + "and contact support if you notice suspicious activity.\n\n"
            + "For your security, your password is not included in this email.");

    try {
        mailSender.send(message);
        logger.info(
                "Failed-login alert email sent for user ID {}.",
                user.getId());
    } catch (MailException exception) {
        logger.error(
                "Unable to send failed-login alert for user ID {}.",
                user.getId(),
                exception);
    }
}


public void sendWelcomeEmail(User user) {

    if (user == null || user.getEmail() == null
            || user.getEmail().isBlank()) {
        logger.warn("Welcome email skipped: user email is missing.");
        return;
    }

    JavaMailSender mailSender =
            mailSenderProvider.getIfAvailable();

    if (mailSender == null
            || fromAddress == null
            || fromAddress.isBlank()) {
        logger.warn(
                "Welcome email skipped: SMTP is not configured.");
        return;
    }

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromAddress);
    message.setTo(user.getEmail());
    message.setSubject("Welcome to RTube!");

    message.setText(
            "Hello " + user.getUsername() + ",\n\n"
            + "Welcome to RTube - Raunak Dummy YouTube!\n\n"
            + "Your account has been created successfully. "
            + "We are happy to have you join our learning community.\n\n"
            + "You can now sign in to your RTube account "
            + "and start exploring videos.\n\n"
            + "Keep learning and enjoy your experience!\n\n"
            + "Best regards,\n"
            + "RTube Team\n"
            + "Raunak Dummy YouTube");

    try {
        mailSender.send(message);
        logger.info(
                "Welcome email sent for user ID {}.",
                user.getId());
    } catch (MailException exception) {
        // Do not undo successful registration if email delivery fails.
        logger.error(
                "Unable to send welcome email for user ID {}.",
                user.getId(),
                exception);
    }
}


public void sendPasswordResetEmail(User user, String resetLink) {

    if (user == null || user.getEmail() == null
            || user.getEmail().isBlank()) {
        logger.warn("Password reset email skipped: user email is missing.");
        return;
    }

    if (resetLink == null || resetLink.isBlank()) {
        logger.warn("Password reset email skipped: reset link is missing.");
        return;
    }

    JavaMailSender mailSender =
            mailSenderProvider.getIfAvailable();

    if (mailSender == null
            || fromAddress == null
            || fromAddress.isBlank()) {
        logger.warn(
                "Password reset email skipped: SMTP is not configured.");
        return;
    }

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromAddress);
    message.setTo(user.getEmail());
    message.setSubject("Reset your RTube password");

    message.setText(
            "Hello " + user.getUsername() + ",\n\n"
            + "We received a request to reset your RTube password.\n\n"
            + "Use the link below to choose a new password:\n\n"
            + resetLink + "\n\n"
            + "This link expires in 15 minutes and can only be used once.\n\n"
            + "If you did not request a password reset, you can ignore "
            + "this email. Your current password will remain unchanged.\n\n"
            + "Best regards,\n"
            + "RTube Team");

    try {
        mailSender.send(message);
        logger.info(
                "Password reset email sent for user ID {}.",
                user.getId());
    } catch (MailException exception) {
        logger.error(
                "Unable to send password reset email for user ID {}.",
                user.getId(),
                exception);
        throw new IllegalStateException(
                "Unable to send the password reset email.");
    }
}

}
