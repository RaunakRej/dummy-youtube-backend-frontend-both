package com.raunak.youtube.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "forward:/login.html";
    }

    @GetMapping({"/", "/index"})
    public String homePage() {
        return "forward:/index.html";
    }

    @GetMapping("/channel")
    public String channelPage() {
        return "forward:/channel.html";
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