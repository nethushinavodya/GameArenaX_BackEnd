package org.example.gamearenax_backend.service;

import org.example.gamearenax_backend.dto.GameDTO;

public interface GameService {
    int saveGame(GameDTO gameDTO);

    Object getAllGames();

    int updateGame(GameDTO gameDTO);  

    int deleteGame(String name);

    Object getGameByName(String name);

    Object getGameByActive(String isActive);
}
