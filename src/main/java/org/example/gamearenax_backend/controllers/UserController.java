package org.example.gamearenax_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.example.gamearenax_backend.dto.AuthDTO;
import org.example.gamearenax_backend.dto.ResponseDTO;
import org.example.gamearenax_backend.dto.UserDTO;
import org.example.gamearenax_backend.service.UserService;
import org.example.gamearenax_backend.service.impl.UserServiceImpl;
import org.example.gamearenax_backend.util.JwtUtil;
import org.example.gamearenax_backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        try {
            int res = userService.saveUser(userDTO);

            switch (res){
                case VarList.Created -> {
                    request.getSession().setAttribute("email", userDTO.getEmail());
                    request.getSession().setAttribute("role", userDTO.getRole());
                    System.out.println(request.getSession().getAttribute("email"));
                    System.out.println(request.getSession().getAttribute("role")+ "kkkkkkkk");
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, " Success",authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used",null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Request,"Error",null));
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllUsers(){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Success", userService.getAllUsers()));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO){
        try {
            System.out.println(userDTO.getCountry() + " " + userDTO.getUsername());
            int res = userService.updateUser(userDTO);
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
}
