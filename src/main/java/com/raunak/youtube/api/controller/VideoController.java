package com.raunak.youtube.api.controller;

import com.raunak.youtube.api.config.AuthSession;
import com.raunak.youtube.api.model.Video;
import com.raunak.youtube.api.service.VideoService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    // =====================================================
    // GET ALL VIDEOS
    // GET /api/videos
    // =====================================================

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    // =====================================================
    // SEARCH VIDEOS API
    // GET /api/videos/search?keyword={keyword}
    // =====================================================

    @GetMapping("/search")
    public List<Video> searchVideos(
            @RequestParam String keyword) {

        return videoService.searchVideos(keyword);
    }

    // =====================================================
    // LIKE VIDEO API
    // POST /api/videos/{id}/like
    // =====================================================

    @PostMapping("/{id}/like")
    public ResponseEntity<Video> likeVideo(
            @PathVariable int id,
            HttpSession session) {

        Video video = videoService.likeVideo(id, session);

        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(video);
    }

    // =====================================================
    // UNLIKE VIDEO API
    // DELETE /api/videos/{id}/like
    // =====================================================

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Video> unlikeVideo(
            @PathVariable int id,
            HttpSession session) {

        Video video = videoService.unlikeVideo(id, session);

        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(video);
    }

    // =====================================================
    // GET LIKED VIDEOS API
    // GET /api/videos/liked
    // =====================================================

    @GetMapping("/liked")
    public List<Video> getLikedVideos(
            HttpSession session) {

        return videoService.getLikedVideos(session);
    }

    // =====================================================
    // ADD VIDEO TO WATCH HISTORY
    // POST /api/videos/{id}/history
    // =====================================================

    @PostMapping("/{id}/history")
    public ResponseEntity<Video> addToHistory(
            @PathVariable int id,
            HttpSession session) {

        Video video = videoService.addToHistory(id, session);

        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(video);
    }

    // =====================================================
    // GET WATCH HISTORY
    // GET /api/videos/history
    // =====================================================

    @GetMapping("/history")
    public List<Video> getWatchHistory(
            HttpSession session) {

        return videoService.getWatchHistory(session);
    }

    // =====================================================
    // DELETE ONE VIDEO FROM WATCH HISTORY
    // DELETE /api/videos/history/{videoId}
    // =====================================================

    @DeleteMapping("/history/{videoId}")
    public ResponseEntity<Void> removeFromHistory(
            @PathVariable int videoId,
            HttpSession session) {

        if (session.getAttribute(
                AuthSession.USER_ID_ATTRIBUTE) == null) {
            return ResponseEntity.status(401).build();
        }

        videoService.removeFromHistory(videoId, session);

        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // CLEAR ALL WATCH HISTORY
    // DELETE /api/videos/history
    // =====================================================

    @DeleteMapping("/history")
    public ResponseEntity<Void> clearWatchHistory(
            HttpSession session) {

        if (session.getAttribute(
                AuthSession.USER_ID_ATTRIBUTE) == null) {
            return ResponseEntity.status(401).build();
        }

        videoService.clearWatchHistory(session);

        return ResponseEntity.noContent().build();
    }

    // =====================================================
    // GET VIDEO BY ID
    // GET /api/videos/{id}
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(
            @PathVariable int id) {

        Video video = videoService.getVideoById(id);

        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(video);
    }
}
