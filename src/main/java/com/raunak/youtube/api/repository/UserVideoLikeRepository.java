package com.raunak.youtube.api.repository;

import com.raunak.youtube.api.model.UserVideoLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserVideoLikeRepository
        extends JpaRepository<UserVideoLike, Integer> {

    // Check whether a user has liked a particular video
    boolean existsByUserIdAndVideoId(int userId, int videoId);

    // Find a particular user's like
    Optional<UserVideoLike> findByUserIdAndVideoId(
            int userId,
            int videoId);

    // Get all likes belonging to a user
    List<UserVideoLike> findByUserId(int userId);
}