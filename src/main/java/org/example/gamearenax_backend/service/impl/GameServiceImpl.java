package org.example.gamearenax_backend.service.impl;

import jakarta.transaction.Transactional;
import org.example.gamearenax_backend.dto.GameDTO;
import org.example.gamearenax_backend.entity.Game;
import org.example.gamearenax_backend.repository.GameRepo;
import org.example.gamearenax_backend.service.GameService;
import org.example.gamearenax_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Optional;

@Service

public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveGame(GameDTO gameDTO) {
        if (gameRepo.existsByName(gameDTO.getName())) {
            return VarList.Not_Acceptable;
        } else {
            gameRepo.save(modelMapper.map(gameDTO, Game.class));
            return VarList.Created;
        }
    }

    @Override
    public Object getAllGames() {
        try {
            return gameRepo.findAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public int updateGame(GameDTO gameDTO) {
        if (gameRepo.existsByName(gameDTO.getName())) {
            Game existingGame = gameRepo.findByName(gameDTO.getName());

            existingGame.setDescription(gameDTO.getDescription());
            existingGame.setGenre(gameDTO.getGenre());
            existingGame.setLogoUrl(gameDTO.getLogoUrl());
            existingGame.setPlatform(gameDTO.getPlatform());
            existingGame.setIsActive(gameDTO.getIsActive());

            gameRepo.save(existingGame);
            return VarList.Created;
        } else {
            return VarList.Not_Acceptable; // No game found to update
        }
    }

    @Override
    @Transactional
    public int deleteGame(String name) {
        if (gameRepo.existsByName(name)) {
            gameRepo.updateIsActive(name);
            return VarList.Created;
        } else {
            return VarList.Not_Acceptable;
        }
    }

    @Override
    @Transactional
    public Object getGameByName(String name) {
        try {
            return gameRepo.findByName(name);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object getGameByActive(String isActive) {
        try {
            return gameRepo.findByIsActive(isActive);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
