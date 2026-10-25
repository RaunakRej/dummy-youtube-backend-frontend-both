package com.raunak.youtube.api.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
public class SecurityConfig {

        // =====================================================
        // PASSWORD ENCODER
        // =====================================================

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // =====================================================
        // SECURITY CONFIGURATION
        // =====================================================

        @Bean
        public SecurityFilterChain securityFilterChain(
                        HttpSecurity http) throws Exception {

                AuthorizationManager<RequestAuthorizationContext> sessionRequired = (authentication,
                                context) -> {
                                        HttpSession session = context.getRequest().getSession(false);
                                        return new AuthorizationDecision(
                                                        session != null && session
                                                                        .getAttribute(AuthSession.USER_ID_ATTRIBUTE) != null);
                                };

                http
                                // Disable CSRF for our REST APIs
                                .csrf(csrf -> csrf.disable())

                                // Permit login, registration, and the static files needed by the login page.
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/login",
                                                                "/login.html",
                                                                "/signup",
                                                                "/signup.html",
                                                                "/reset-password",
                                                                "/reset-password.html",
                                                                "/api/auth/login",
                                                                "/api/auth/register",
                                                                "/api/auth/password-reset/request",
                                                                "/api/auth/password-reset/confirm",
                                                                "/*.css",
                                                                "/*.js",
                                                                "/youtube-icon.svg",
                                                                "/images/**",
                                                                "/favicon.ico")
                                                .permitAll()
                                                .anyRequest()
                                                .access(sessionRequired))
                                .exceptionHandling(exceptions -> exceptions
                                                .authenticationEntryPoint((request, response, exception) -> {
                                                        if (request.getRequestURI().startsWith(
                                                                        request.getContextPath() + "/api/")) {
                                                                response.sendError(401);
                                                        } else {
                                                                response.sendRedirect(
                                                                                request.getContextPath() + "/login");
                                                        }
                                                }));

                http
                                .formLogin(form -> form.disable())
                                .httpBasic(basic -> basic.disable());

                return http.build();
        }
}
