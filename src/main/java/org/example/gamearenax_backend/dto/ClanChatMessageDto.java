package org.example.gamearenax_backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClanChatMessageDto {
    private UUID senderId;
    private UUID clanId;
    private String senderName;
    private String message;
    private LocalDateTime sentAt;
}