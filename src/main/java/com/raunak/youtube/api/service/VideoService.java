package com.raunak.youtube.api.service;

import com.raunak.youtube.api.model.Video;
import com.raunak.youtube.api.repository.VideoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

        private final VideoRepository videoRepository;

        // =====================================================
        // CONSTRUCTOR
        // =====================================================

        public VideoService(VideoRepository videoRepository) {
                this.videoRepository = videoRepository;
        }

        // =====================================================
        // GET ALL VIDEOS
        // =====================================================

        public List<Video> getAllVideos() {
                return videoRepository.findAll();
        }

        // =====================================================
        // SEARCH VIDEOS
        // =====================================================

        public List<Video> searchVideos(String keyword) {

                if (keyword == null || keyword.trim().isEmpty()) {
                        return videoRepository.findAll();
                }

                return videoRepository
                                .findByTitleContainingIgnoreCaseOrChannelContainingIgnoreCase(
                                                keyword,
                                                keyword);
        }

        // =====================================================
        // GET VIDEO BY ID
        // =====================================================

        public Video getVideoById(int id) {
                return videoRepository.findById(id).orElse(null);
        }

        // =====================================================
        // LIKE VIDEO
        // =====================================================

        public Video likeVideo(int id) {

                Video video = videoRepository.findById(id).orElse(null);

                if (video == null) {
                        return null;
                }

                video.setLiked(true);

                return videoRepository.save(video);
        }

        // =====================================================
        // UNLIKE VIDEO
        // =====================================================

        public Video unlikeVideo(int id) {

                Video video = videoRepository.findById(id).orElse(null);

                if (video == null) {
                        return null;
                }

                video.setLiked(false);

                return videoRepository.save(video);
        }

        // =====================================================
        // GET LIKED VIDEOS
        // =====================================================

        public List<Video> getLikedVideos() {
                return videoRepository.findByLikedTrue();
        }
}