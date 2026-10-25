package com.raunak.youtube.api.service;

import com.raunak.youtube.api.config.AuthSession;
import com.raunak.youtube.api.model.UserVideoHistory;
import com.raunak.youtube.api.model.UserVideoLike;
import com.raunak.youtube.api.model.Video;
import com.raunak.youtube.api.repository.UserVideoHistoryRepository;
import com.raunak.youtube.api.repository.UserVideoLikeRepository;
import com.raunak.youtube.api.repository.VideoRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserVideoLikeRepository userVideoLikeRepository;
    private final UserVideoHistoryRepository userVideoHistoryRepository;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public VideoService(
            VideoRepository videoRepository,
            UserVideoLikeRepository userVideoLikeRepository,
            UserVideoHistoryRepository userVideoHistoryRepository) {

        this.videoRepository = videoRepository;
        this.userVideoLikeRepository = userVideoLikeRepository;
        this.userVideoHistoryRepository = userVideoHistoryRepository;
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

    public Video likeVideo(int id, HttpSession session) {

        Integer userId = getLoggedInUserId(session);

        if (userId == null) {
            return null;
        }

        Video video = videoRepository.findById(id).orElse(null);

        if (video == null) {
            return null;
        }

        // Prevent duplicate likes
        if (!userVideoLikeRepository
                .existsByUserIdAndVideoId(userId, id)) {

            UserVideoLike like =
                    new UserVideoLike(userId, id);

            userVideoLikeRepository.save(like);
        }

        return video;
    }

    // =====================================================
    // UNLIKE VIDEO
    // =====================================================

    public Video unlikeVideo(int id, HttpSession session) {

        Integer userId = getLoggedInUserId(session);

        if (userId == null) {
            return null;
        }

        Video video = videoRepository.findById(id).orElse(null);

        if (video == null) {
            return null;
        }

        userVideoLikeRepository
                .findByUserIdAndVideoId(userId, id)
                .ifPresent(userVideoLikeRepository::delete);

        return video;
    }

    // =====================================================
    // GET LIKED VIDEOS
    // =====================================================

    public List<Video> getLikedVideos(HttpSession session) {

        Integer userId = getLoggedInUserId(session);

        if (userId == null) {
            return new ArrayList<>();
        }

        List<UserVideoLike> likes =
                userVideoLikeRepository.findByUserId(userId);

        List<Video> likedVideos = new ArrayList<>();

        for (UserVideoLike like : likes) {

            videoRepository.findById(like.getVideoId())
                    .ifPresent(likedVideos::add);
        }

        return likedVideos;
    }

    // =====================================================
    // ADD VIDEO TO WATCH HISTORY
    // =====================================================

    public Video addToHistory(int videoId, HttpSession session) {

        Integer userId = getLoggedInUserId(session);

        if (userId == null) {
            return null;
        }

        Video video = videoRepository.findById(videoId).orElse(null);

        if (video == null) {
            return null;
        }

        long currentTime = System.currentTimeMillis();

        UserVideoHistory history =
                userVideoHistoryRepository
                        .findByUserIdAndVideoId(userId, videoId)
                        .orElse(null);

        // First time watching this video
        if (history == null) {

            history = new UserVideoHistory(
                    userId,
                    videoId,
                    currentTime
            );

        } else {

            // Video already exists in history.
            // Update the watch time.
            history.setWatchedAt(currentTime);
        }

        userVideoHistoryRepository.save(history);

        return video;
    }

    // =====================================================
    // GET USER WATCH HISTORY
    // =====================================================

    public List<Video> getWatchHistory(HttpSession session) {

        Integer userId = getLoggedInUserId(session);

        if (userId == null) {
            return new ArrayList<>();
        }

        List<UserVideoHistory> history =
                userVideoHistoryRepository
                        .findByUserIdOrderByWatchedAtDesc(userId);

        List<Video> historyVideos = new ArrayList<>();

        for (UserVideoHistory item : history) {

            videoRepository.findById(item.getVideoId())
                    .ifPresent(historyVideos::add);
        }

        return historyVideos;
    }

    @Transactional
    public void removeFromHistory(int videoId, HttpSession session) {
        Integer userId = getLoggedInUserId(session);

        if (userId != null) {
            userVideoHistoryRepository.deleteByUserIdAndVideoId(userId, videoId);
        }
    }

    @Transactional
    public void clearWatchHistory(HttpSession session) {
        Integer userId = getLoggedInUserId(session);

        if (userId != null) {
            userVideoHistoryRepository.deleteByUserId(userId);
        }
    }

    // =====================================================
    // CHECK IF USER IS LOGGED IN
    // =====================================================

    private Integer getLoggedInUserId(HttpSession session) {

        if (session == null) {
            return null;
        }

        Object userId =
                session.getAttribute(AuthSession.USER_ID_ATTRIBUTE);

        if (userId instanceof Integer) {
            return (Integer) userId;
        }

        return null;
    }
}