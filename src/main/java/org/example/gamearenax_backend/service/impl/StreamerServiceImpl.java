package org.example.gamearenax_backend.service.impl;

import jakarta.transaction.Transactional;
import org.example.gamearenax_backend.dto.StreamerDTO;
import org.example.gamearenax_backend.entity.Streamer;
import org.example.gamearenax_backend.entity.User;
import org.example.gamearenax_backend.repository.StreamerRepo;
import org.example.gamearenax_backend.repository.UserRepo;
import org.example.gamearenax_backend.service.StreamerService;
import org.example.gamearenax_backend.service.UserService;
import org.example.gamearenax_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StreamerServiceImpl implements StreamerService {
    @Autowired
    private StreamerRepo streamerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Override

    public int addStreamer(StreamerDTO streamerDTO, User user) {
        try {
            if (streamerRepo.existsByEmail(streamerDTO.getEmail())){
                return VarList.Not_Acceptable;
            }else {
                Streamer streamer = modelMapper.map(streamerDTO, Streamer.class);
                streamer.setUser(user);
                streamerRepo.save(streamer);
                userRepo.updateRole(streamerDTO.getEmail(), "Streamer");
                return VarList.Created;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
