package org.example.gamearenax_backend.service;

import org.example.gamearenax_backend.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);

    Object getAllUsers();
}
