package com.raunak.youtube.api.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "user_video_likes",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "video_id"})
    }
)
public class UserVideoLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "video_id", nullable = false)
    private int videoId;

    // =====================================================
    // DEFAULT CONSTRUCTOR
    // =====================================================

    public UserVideoLike() {
    }

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public UserVideoLike(int userId, int videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }

    // =====================================================
    // GETTERS AND SETTERS
    // =====================================================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
}