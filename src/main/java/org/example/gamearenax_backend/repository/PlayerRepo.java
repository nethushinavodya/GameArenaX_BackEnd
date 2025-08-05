package org.example.gamearenax_backend.repository;

import org.example.gamearenax_backend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepo extends JpaRepository<Player, String> {
}
