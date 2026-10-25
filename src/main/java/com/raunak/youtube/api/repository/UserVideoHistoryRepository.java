package com.raunak.youtube.api.repository;

import com.raunak.youtube.api.model.UserVideoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserVideoHistoryRepository
        extends JpaRepository<UserVideoHistory, Integer> {

    Optional<UserVideoHistory> findByUserIdAndVideoId(
            int userId,
            int videoId
    );

    List<UserVideoHistory> findByUserIdOrderByWatchedAtDesc(
            int userId
    );

    void deleteByUserIdAndVideoId(int userId, int videoId);

    void deleteByUserId(int userId);
}