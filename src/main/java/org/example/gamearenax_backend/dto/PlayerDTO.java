package org.example.gamearenax_backend.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {
    private String email;
    private String playerName;
    private String role;
    private String country;

    private String imageUrl;
    private boolean isOnline;
    private String about;

    private int totalMatches;
    private int wins;
    private String rank;

    private String status;
    private List<String> games;

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

}