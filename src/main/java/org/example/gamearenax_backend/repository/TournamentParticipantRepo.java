package org.example.gamearenax_backend.repository;

import org.example.gamearenax_backend.entity.Clan;
import org.example.gamearenax_backend.entity.Player;
import org.example.gamearenax_backend.entity.Tournament;
import org.example.gamearenax_backend.entity.TournamentParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentParticipantRepo extends JpaRepository<TournamentParticipant, Long> {
    long countByTournament(Tournament tournament);
    boolean existsByTournamentAndClan(Tournament tournament, Clan clan);
    boolean existsByTournamentAndPlayer(Tournament tournament, Player player);
}
