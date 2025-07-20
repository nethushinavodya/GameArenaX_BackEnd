package org.example.gamearenax_backend.service;

import org.example.gamearenax_backend.dto.UserDTO;
import org.example.gamearenax_backend.entity.User;

public interface UserService {
    int saveUser(UserDTO userDTO);

    Object getAllUsers();

    int updateUser(UserDTO userDTO);

    User SearchByEmail(String email);
}
