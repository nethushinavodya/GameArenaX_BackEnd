package org.example.gamearenax_backend.repository;

import org.example.gamearenax_backend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepo extends JpaRepository<Player, String> {
    List<Player> findByOnline(boolean b);

    Object findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE Player p SET p.player_name = ?1, p.country = ?2, p.about = ?3, p.total_matches = ?4, p.wins = ?5, p.rank = ?6 WHERE p.email = ?7", nativeQuery = true)
    void updatePlayer(String playerName, String country, String about, int totalMatches, int wins, String rank, String email);

    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "UPDATE Player s SET s.is_online  = true WHERE s.email = ?1", nativeQuery = true)
    void updateIsLive(String email);

    @Modifying
    @Query(value = "UPDATE Player s SET s.is_online  = false WHERE s.email = ?1", nativeQuery = true)
    void updateIsLiveFalse(String email);
}
