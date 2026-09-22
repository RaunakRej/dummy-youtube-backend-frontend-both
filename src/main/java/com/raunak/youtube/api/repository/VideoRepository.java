package com.raunak.youtube.api.repository;

import com.raunak.youtube.api.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Integer> {

    // Search by title or channel
    List<Video> findByTitleContainingIgnoreCaseOrChannelContainingIgnoreCase(
            String title,
            String channel);

    // Get liked videos
    List<Video> findByLikedTrue();
}