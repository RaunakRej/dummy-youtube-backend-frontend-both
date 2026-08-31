package com.raunak.youtube.api.controller;

import com.raunak.youtube.api.model.Video;
import com.raunak.youtube.api.service.VideoService;

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
    // =====================================================

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    // =====================================================
    // SEARCH VIDEOS API
    // =====================================================

    @GetMapping("/search")
    public List<Video> searchVideos(
            @RequestParam String keyword) {

        return videoService.searchVideos(keyword);
    }

    // =====================================================
    // LIKE VIDEO API
    // =====================================================

    @PostMapping("/{id}/like")
    public ResponseEntity<Video> likeVideo(
            @PathVariable int id) {

        Video video = videoService.likeVideo(id);

        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(video);
    }

    // =====================================================
    // UNLIKE VIDEO API
    // =====================================================

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Video> unlikeVideo(
            @PathVariable int id) {

        Video video = videoService.unlikeVideo(id);

        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(video);
    }

    // =====================================================
    // GET LIKED VIDEOS API
    // =====================================================

    @GetMapping("/liked")
    public List<Video> getLikedVideos() {

        return videoService.getLikedVideos();
    }

    // =====================================================
    // GET VIDEO BY ID API
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
