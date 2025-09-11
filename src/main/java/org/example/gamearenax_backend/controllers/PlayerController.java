package org.example.gamearenax_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.gamearenax_backend.dto.PlayerDTO;
import org.example.gamearenax_backend.dto.ResponseDTO;
import org.example.gamearenax_backend.dto.UserDTO;
import org.example.gamearenax_backend.entity.User;
import org.example.gamearenax_backend.service.PlayerService;
import org.example.gamearenax_backend.service.StreamerService;
import org.example.gamearenax_backend.service.UserService;
import org.example.gamearenax_backend.util.JwtUtil;
import org.example.gamearenax_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/player")
@CrossOrigin
public class PlayerController {

    private final JwtUtil jwtUtil;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public PlayerController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/addPlayer")
    public ResponseEntity<ResponseDTO> addPlayer(@RequestBody PlayerDTO playerDTO, HttpServletRequest request) {

        String email = (String) request.getAttribute("email");
        String role = (String) request.getAttribute("role");

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Email not found in token", null));
        }

        User user = userService.SearchByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
        }

        System.out.println("email: " + email);
        System.out.println("role: " + role);
        System.out.println(user.getUsername());

        try {
            int res = playerService.addPlayer(playerDTO, user);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(modelMapper.map(user, UserDTO.class));
                    request.setAttribute("role", "Player");
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", token));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Request, "Error", null));
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getAllPlayers")
    public ResponseEntity<ResponseDTO> getAllPlayers() {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", playerService.getAllPlayers()));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getByOnline")
    public ResponseEntity<ResponseDTO> getByOnline() {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", playerService.getByOnline()));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<ResponseDTO> getPlayerByEmail(@RequestParam String email) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", playerService.getPlayerByEmail(email)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/updatePlayer")
    public ResponseEntity<ResponseDTO> updatePlayer(@RequestBody PlayerDTO playerDTO) {
        try {
            int res = playerService.updatePlayer(playerDTO);
            switch (res) {
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

    @PutMapping("/updateIsLive")
    public ResponseEntity<ResponseDTO> updateByOnline(@RequestParam String email){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created,"Success",playerService.updateIsLive(email)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @PatchMapping("/updateIsLiveFalse")
    public ResponseEntity<ResponseDTO> updateByOnlineFalse(@RequestParam String email){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created,"Success",playerService.updateIsLiveFalse(email)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    /*ban*/
    @PutMapping("/ban")
    public ResponseEntity<ResponseDTO> banPlayer(@RequestParam String email){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created,"Success",playerService.banPlayer(email)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /*unban*/
    @PutMapping("/unban")
    public ResponseEntity<ResponseDTO> unbanPlayer(@RequestParam String email){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created,"Success",playerService.unbanPlayer(email)));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
