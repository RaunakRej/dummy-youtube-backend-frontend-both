
package com.raunak.youtube.api.repository;

import com.raunak.youtube.api.model.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoCommentRepository
        extends JpaRepository<VideoComment, Integer> {

    // Retrieve comments for a video, newest first.
    List<VideoComment> findByVideo_IdOrderByCreatedAtDesc(int videoId);

    // Retrieve a comment by its ID and its owner's ID.
    java.util.Optional<VideoComment> findByIdAndUser_Id(
            int commentId,
            int userId);
}
