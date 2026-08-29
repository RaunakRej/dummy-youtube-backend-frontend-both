package com.raunak.youtube.api.model;

public class Video {

    private int id;
    private String title;
    private String channel;
    private String views;
    private String uploaded;
    private String duration;
    private String thumbnail;
    private String category;
    private String videoUrl;
    private boolean liked;

    // =====================================================
    // DEFAULT CONSTRUCTOR
    // =====================================================

    public Video() {
    }

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public Video(
            int id,
            String title,
            String channel,
            String views,
            String uploaded,
            String duration,
            String thumbnail,
            String category,
            String videoUrl) {

        this.id = id;
        this.title = title;
        this.channel = channel;
        this.views = views;
        this.uploaded = uploaded;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.category = category;
        this.videoUrl = videoUrl;
        this.liked = false;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // =====================================================
    // VIDEO URL
    // =====================================================

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    // =====================================================
    // LIKE
    // =====================================================

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}