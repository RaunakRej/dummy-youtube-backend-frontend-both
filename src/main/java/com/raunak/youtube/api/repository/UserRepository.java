package com.raunak.youtube.api.repository;

import com.raunak.youtube.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPasswordResetTokenHash(String passwordResetTokenHash);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameAndIdNot(String username, int id);

    boolean existsByEmailAndIdNot(String email, int id);
}