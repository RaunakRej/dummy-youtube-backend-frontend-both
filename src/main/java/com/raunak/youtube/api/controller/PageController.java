package com.raunak.youtube.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "forward:/login.html";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "forward:/signup.html";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "forward:/reset-password.html";
    }

    @GetMapping({"/", "/index"})
    public String homePage() {
        return "forward:/index.html";
    }

    @GetMapping("/channel")
    public String channelPage() {
        return "forward:/channel.html";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "forward:/profile.html";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/history")
    public String historyPage() {
        return "forward:/history.html";
    }

    @GetMapping({"/likedvideos", "/likedvideo"})
    public String likedVideosPage() {
        return "forward:/likedvideos.html";
    }

    @GetMapping("/watchlater")
    public String watchLaterPage() {
        return "forward:/watchlater.html";
    }

    @GetMapping("/downloads")
    public String downloadsPage() {
        return "forward:/downloads.html";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "forward:/upload.html";
    }

    @GetMapping("/settings")
    public String settingsPage() {
        return "forward:/settings.html";
    }

    @GetMapping("/video")
    public String videoPage() {
        return "forward:/video.html";
    }
}