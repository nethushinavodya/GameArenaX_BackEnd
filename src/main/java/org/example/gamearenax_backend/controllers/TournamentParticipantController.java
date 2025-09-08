package org.example.gamearenax_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.gamearenax_backend.dto.ResponseDTO;
import org.example.gamearenax_backend.dto.TournamentParticipantDTO;
import org.example.gamearenax_backend.service.TournamentParticipantService;
import org.example.gamearenax_backend.util.VarList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tournament-participant")
@RequiredArgsConstructor
public class TournamentParticipantController {
    private final TournamentParticipantService tournamentParticipantService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> joinTournament(@RequestBody TournamentParticipantDTO tournamentParticipantDTO) {
        try {
            TournamentParticipantDTO joinedTournament = tournamentParticipantService.joinTournament(tournamentParticipantDTO);
            return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Joined tournament successfully", joinedTournament));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(VarList.Not_Acceptable, e.getMessage(), null));
        }
    }
}
