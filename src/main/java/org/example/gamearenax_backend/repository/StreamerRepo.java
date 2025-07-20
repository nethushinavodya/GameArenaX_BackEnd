package org.example.gamearenax_backend.repository;

import org.example.gamearenax_backend.entity.Streamer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamerRepo extends JpaRepository<Streamer, String> {
    boolean existsByEmail(String email);
}
