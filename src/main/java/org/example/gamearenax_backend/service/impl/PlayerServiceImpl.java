package org.example.gamearenax_backend.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.example.gamearenax_backend.dto.PlayerDTO;
import org.example.gamearenax_backend.entity.Player;
import org.example.gamearenax_backend.entity.User;
import org.example.gamearenax_backend.repository.PlayerRepo;
import org.example.gamearenax_backend.repository.UserRepo;
import org.example.gamearenax_backend.service.PlayerService;
import org.example.gamearenax_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @PostConstruct
    public void configureMapper() {
        modelMapper.typeMap(PlayerDTO.class, Player.class)
                .addMappings(mapper -> mapper.map(PlayerDTO::isOnline, Player::setIsOnline));
    }

    @Autowired
    private PlayerRepo playerRepo;
    @Override
    public int addPlayer(PlayerDTO playerDTO, User user) {
        try {
            System.out.println("isOnline: " + playerDTO.isOnline());
            if(userRepo.existsByEmail(playerDTO.getEmail())) {
                Player player = modelMapper.map(playerDTO, Player.class);
                player.setUser(user);
                player.setIsOnline(playerDTO.isOnline());
                playerRepo.save(player);
                userRepo.updatePlayerRole(playerDTO.getEmail(), "Player");
                return VarList.Created;
            } else {
                return VarList.Not_Acceptable;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public Object getAllPlayers() {
        try {
            return playerRepo.findAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object getByOnline() {
        try {
            List<Player> players = playerRepo.findByIsOnline(true);
            if (players.isEmpty()) {
                return "No players are online";
            } else {
                return players;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object getPlayerByEmail(String email) {
        try {
            return playerRepo.findByEmail(email);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int updatePlayer(PlayerDTO playerDTO) {
        try {
            playerRepo.updatePlayer(playerDTO.getPlayerName(), playerDTO.getCountry(), playerDTO.getAbout(),playerDTO.getTotalMatches(), playerDTO.getWins(), playerDTO.getRank(), playerDTO.getEmail());
            return VarList.Created;
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object updateIsLive(String email) {
        try {
            if (playerRepo.existsByEmail(email)){
                playerRepo.updateIsLive(email);
                return VarList.Created;
            }else {
                return "Player Not Found";
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object updateIsLiveFalse(String email) {
        try {
            if (playerRepo.existsByEmail(email)){
                playerRepo.updateIsLiveFalse(email);
                return VarList.Created;
            }else {
                return "Player Not Found";
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object banPlayer(String email) {
        try {
            if (playerRepo.existsByEmail(email)){
                playerRepo.updateStatus(email);
                return VarList.Created;
            }else {
                return "Player Not Found";
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Object unbanPlayer(String email) {
        try {
            if (playerRepo.existsByEmail(email)){
                playerRepo.updateStatusActive(email);
                return VarList.Created;
            }else {
                return "Player Not Found";
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
