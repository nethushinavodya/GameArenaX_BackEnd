package org.example.gamearenax_backend.repository;

import org.example.gamearenax_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
