package com.flagship.backend.Respositories;

import com.flagship.backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByApiKey(String apiKey);
    Optional<User> findUserByUsername(String username);
    boolean existsByUsername(String username);
}
