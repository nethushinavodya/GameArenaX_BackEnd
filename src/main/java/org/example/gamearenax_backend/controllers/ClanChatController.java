package org.example.gamearenax_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.gamearenax_backend.dto.ClanChatMessageDto;
/*import org.example.gamearenax_backend.entity.Clan;
import org.example.gamearenax_backend.entity.ClanChatMessage;
import org.example.gamearenax_backend.entity.Player;
import org.example.gamearenax_backend.repository.ClanChatMessageRepository;
import org.example.gamearenax_backend.repository.ClanRepository;
import org.example.gamearenax_backend.repository.PlayerRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;*/
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ClanChatController {

    private final SimpMessagingTemplate messagingTemplate;
    //private final ClanChatMessageRepository chatRepo;
    //private final PlayerRepository playerRepo;
    //private final ClanRepository clanRepo;

    @MessageMapping("/clan/chat")
    public void sendMessage(ClanChatMessageDto dto) {
        System.out.println("Message received: " + dto.getMessage()+ "1️⃣1️⃣1️⃣");
      /*  Player sender = playerRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Player not found"));
        Clan clan = clanRepo.findById(dto.getClanId())
                .orElseThrow(() -> new RuntimeException("Clan not found"));

        // Save to DB
        ClanChatMessage message = ClanChatMessage.builder()
                .sender(sender)
                .clan(clan)
                .message(dto.getMessage())
                .sentAt(LocalDateTime.now())
                .build();
        chatRepo.save(message);

        // Broadcast to clan topic
        dto.setSenderName(sender.getPlayerName());
        dto.setSentAt(LocalDateTime.now());
        messagingTemplate.convertAndSend("/topic/clan/" + dto.getClanId() + "/messages", dto);
*/    }
}