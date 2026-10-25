package com.raunak.youtube.api.controller;

import com.raunak.youtube.api.config.AuthSession;
import com.raunak.youtube.api.model.User;
import com.raunak.youtube.api.service.AuthService;
import com.raunak.youtube.api.service.LoginNotificationService;
import com.raunak.youtube.api.service.PasswordResetService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final LoginNotificationService loginNotificationService;
    private final PasswordResetService passwordResetService;

    public AuthController(
            AuthService authService,
            LoginNotificationService loginNotificationService,
            PasswordResetService passwordResetService) {
        this.authService = authService;
        this.loginNotificationService = loginNotificationService;
        this.passwordResetService = passwordResetService;
    }

    // =====================================================
    // REGISTER
    // =====================================================

    @PostMapping("/register")
public ResponseEntity<?> register(
        @RequestBody RegisterRequest request) {

    try {
        User user = authService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        // Send welcome email after successful registration.
        loginNotificationService.sendWelcomeEmail(user);

        return ResponseEntity.ok(user);

    } catch (RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}

    // =====================================================
    // LOGIN
    // =====================================================

   
@PostMapping("/login")
public ResponseEntity<?> login(
        @RequestBody LoginRequest request,
        HttpServletRequest httpRequest) {

    try {
        User user = authService.loginUser(
                request.getUsername(),
                request.getPassword(),
httpRequest
        );

        HttpSession session = httpRequest.getSession(true);
        httpRequest.changeSessionId();

        session.setAttribute(
                AuthSession.USER_ID_ATTRIBUTE,
                user.getId()
        );

        // Send login alert with browser/device information
        loginNotificationService.sendLoginAlert(user, httpRequest);

        return ResponseEntity.ok(user);

    } catch (RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}

@PostMapping("/password-reset/request")
public ResponseEntity<?> requestPasswordReset(
        @RequestBody PasswordResetRequest request) {
    try {
        passwordResetService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok(Map.of(
                "message",
                "If an account exists for that email, password reset instructions will be sent."));
    } catch (IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "message", exception.getMessage()));
    } catch (IllegalStateException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "message",
                "Password recovery email is unavailable right now. Please try again later."));
    }
}

@PostMapping("/password-reset/confirm")
public ResponseEntity<?> confirmPasswordReset(
        @RequestBody PasswordResetConfirmation request) {
    try {
        passwordResetService.resetPassword(
                request.getToken(),
                request.getNewPassword());
        return ResponseEntity.ok(Map.of(
                "message", "Your password has been reset. You can now sign in."));
    } catch (IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "message", exception.getMessage()));
    }
}


@PutMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable int id,
            HttpSession session,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            @RequestParam(value = "newPassword", defaultValue = "") String newPassword,
            @RequestParam(value = "confirmNewPassword", defaultValue = "") String confirmNewPassword) {
        if (!isCurrentSessionUser(session, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can only update your own profile.");
        }
        try {
            User user = authService.updateUserProfile(
                    id,
                    currentPassword,
                    username,
                    email,
                    profilePicture,
                    newPassword,
                    confirmNewPassword);
            return ResponseEntity.ok(user);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Unable to read the uploaded profile picture.");
        } catch (IllegalArgumentException e) {
            HttpStatus status = "User not found.".equals(e.getMessage())
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(e.getMessage());
        }
    }

    @GetMapping("/profile/{id}/picture")
    public ResponseEntity<byte[]> getProfilePicture(
            @PathVariable int id,
            HttpSession session) {
        if (!isCurrentSessionUser(session, id)) {
            return ResponseEntity.notFound().build();
        }
        try {
            User user = authService.getUserProfile(id);
            byte[] picture = user.getProfilePicture();
            String contentType = user.getProfilePictureContentType();
            if (picture == null || contentType == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CACHE_CONTROL, "no-store")
                    .body(picture);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.noContent().build();
    }

    private boolean isCurrentSessionUser(HttpSession session, int id) {
        return session != null
                && Integer.valueOf(id).equals(session.getAttribute(AuthSession.USER_ID_ATTRIBUTE));
    }

    // =====================================================
    // REGISTER REQUEST
    // =====================================================

    public static class RegisterRequest {

        private String username;
        private String email;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // =====================================================
    // LOGIN REQUEST
    // =====================================================

    public static class LoginRequest {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class PasswordResetRequest {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class PasswordResetConfirmation {
        private String token;
        private String newPassword;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}