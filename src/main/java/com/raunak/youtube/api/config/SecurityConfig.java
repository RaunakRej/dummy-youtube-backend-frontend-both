package com.raunak.youtube.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

                http
                                // Disable CSRF for our REST APIs
                                .csrf(csrf -> csrf.disable())

                                // Configure API access
                                .authorizeHttpRequests(auth -> auth

                                                // Authentication APIs are public
                                                .requestMatchers("/api/auth/**").permitAll()

                                                // Video APIs are public
                                                .requestMatchers("/api/videos/**").permitAll()

                                                // Login page
                                                .requestMatchers("/login").permitAll()

                                                // Frontend files
                                                .requestMatchers(
                                                                "/",
                                                                "/login",
                                                                "/index",
                                                                "/channel",
                                                                "/history",
                                                                "/likedvideos",
                                                                "/likedvideo",
                                                                "/watchlater",
                                                                "/downloads",
                                                                "/upload",
                                                                "/settings",
                                                                "/video",
                                                                "/index.html",
                                                                "/*.html",
                                                                "/*.css",
                                                                "/*.js",
                                                                "/images/**")
                                                .permitAll()

                                                // Everything else requires authentication
                                                .anyRequest().authenticated())

                                // Disable default Spring Security login page
                                .formLogin(form -> form.disable())

                                // Disable HTTP Basic authentication
                                .httpBasic(basic -> basic.disable());

                return http.build();
        }
}
