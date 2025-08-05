package org.example.gamearenax_backend.service;

import org.example.gamearenax_backend.dto.PlayerDTO;
import org.example.gamearenax_backend.entity.User;

public interface PlayerService {
    int addPlayer(PlayerDTO playerDTO, User user);
}
