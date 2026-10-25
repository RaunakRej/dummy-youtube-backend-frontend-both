
package com.raunak.youtube.api.controller;

import com.raunak.youtube.api.config.AuthSession;
import com.raunak.youtube.api.model.User;
import com.raunak.youtube.api.model.Video;
import com.raunak.youtube.api.model.VideoComment;
import com.raunak.youtube.api.repository.UserRepository;
import com.raunak.youtube.api.repository.VideoCommentRepository;
import com.raunak.youtube.api.repository.VideoRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VideoCommentController {

    private final VideoCommentRepository commentRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public VideoCommentController(
            VideoCommentRepository commentRepository,
            VideoRepository videoRepository,
            UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    // GET COMMENTS FOR A VIDEO
    @GetMapping("/videos/{videoId}/comments")
    public ResponseEntity<?> getComments(
            @PathVariable int videoId) {

        if (!videoRepository.existsById(videoId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Video not found."));
        }

        List<CommentResponse> comments =
                commentRepository
                        .findByVideo_IdOrderByCreatedAtDesc(videoId)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(comments);
    }

    // ADD A COMMENT — LOGIN REQUIRED
    @PostMapping("/videos/{videoId}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable int videoId,
            @RequestBody CommentRequest request,
            HttpSession session) {

        Object sessionUserId =
                session.getAttribute(AuthSession.USER_ID_ATTRIBUTE);

        if (!(sessionUserId instanceof Integer userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please log in to comment."));
        }

        if (request == null
                || request.getText() == null
                || request.getText().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Comment cannot be empty."));
        }

        String text = request.getText().trim();

        if (text.length() > 2000) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "Comment must be 2000 characters or fewer."));
        }

        Video video = videoRepository.findById(videoId)
                .orElse(null);

        if (video == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Video not found."));
        }

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            session.invalidate();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message",
                            "Your session is no longer valid. Please log in again."));
        }

        VideoComment comment = new VideoComment();
        comment.setVideo(video);
        comment.setUser(user);
        comment.setText(text);
        comment.setCreatedAt(LocalDateTime.now());

        VideoComment savedComment =
                commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(savedComment));
    }

    // DELETE YOUR OWN COMMENT — LOGIN REQUIRED
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable int commentId,
            HttpSession session) {

        Object sessionUserId =
                session.getAttribute(AuthSession.USER_ID_ATTRIBUTE);

        if (!(sessionUserId instanceof Integer userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please log in first."));
        }

        VideoComment comment = commentRepository
                .findByIdAndUser_Id(commentId, userId)
                .orElse(null);

        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message",
                            "Comment not found or you do not own it."));
        }

        commentRepository.delete(comment);

        return ResponseEntity.ok(
                Map.of("message", "Comment deleted successfully."));
    }

    // RETURN ONLY SAFE COMMENT FIELDS
    private CommentResponse toResponse(VideoComment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getText(),
                comment.getCreatedAt()
        );
    }

    // REQUEST DTO
    public static class CommentRequest {

        private String text;

        public CommentRequest() {
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    // RESPONSE DTO
    public static class CommentResponse {

        private final int id;
        private final int userId;
        private final String username;
        private final String text;
        private final LocalDateTime createdAt;

        public CommentResponse(
                int id,
                int userId,
                String username,
                String text,
                LocalDateTime createdAt) {

            this.id = id;
            this.userId = userId;
            this.username = username;
            this.text = text;
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getText() {
            return text;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
