package org.example.gamearenax_backend.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TournamentDTO {
    private Long id;
    private String name;
    private String description;
    private String gameName;

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal prizePool;

    private String status;
    private String type;
    private int maxParticipants;
}
