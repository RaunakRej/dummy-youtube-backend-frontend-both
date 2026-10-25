package com.raunak.youtube.api.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "user_video_history",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "video_id"})
    }
)
public class UserVideoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "video_id", nullable = false)
    private int videoId;

    @Column(name = "watched_at", nullable = false)
    private long watchedAt;

    public UserVideoHistory() {
    }

    public UserVideoHistory(int userId, int videoId, long watchedAt) {
        this.userId = userId;
        this.videoId = videoId;
        this.watchedAt = watchedAt;
    }

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

    public long getWatchedAt() {
        return watchedAt;
    }

    public void setWatchedAt(long watchedAt) {
        this.watchedAt = watchedAt;
    }
}