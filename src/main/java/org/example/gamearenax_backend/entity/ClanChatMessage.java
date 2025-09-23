package org.example.gamearenax_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "clan_chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClanChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Sender Player
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player sender;

    // Target Clan
    @ManyToOne
    @JoinColumn(name = "clan_id", nullable = false)
    private Clan clan;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();
}