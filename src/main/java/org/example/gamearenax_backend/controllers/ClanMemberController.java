package org.example.gamearenax_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.example.gamearenax_backend.dto.ResponseDTO;
import org.example.gamearenax_backend.service.ClanMemberService;
import org.example.gamearenax_backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/clan-member")
@RequiredArgsConstructor
public class ClanMemberController {
    private final ClanMemberService clanMemberService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDTO> joinClan(@RequestParam("playerId") String playerId, @RequestParam("clanId") String clanId){
        System.out.println(playerId + " " + clanId);
        try {

              int res = clanMemberService.joinClan(playerId, clanId);
              switch (res){
                  case VarList.Created -> {
                      return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", null));
                  }
                  default -> {
                      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Request, "Error", null));
                  }
              }
          } catch (RuntimeException e) {
              e.printStackTrace();
              throw new RuntimeException(e.getMessage());
          }
    }
    //update role
    @PatchMapping("/updateRole")
    public ResponseEntity<ResponseDTO> updateRole(@RequestParam("playerId") String playerId, @RequestParam("clanId") String clanId, @RequestParam("role") String role){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", clanMemberService.updateRole(playerId, clanId, role)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    @DeleteMapping("/leave")
    public ResponseEntity<ResponseDTO> leaveClan(@RequestParam("playerId") String playerId, @RequestParam("clanId") String clanId){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", clanMemberService.leaveClan(playerId, clanId)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
