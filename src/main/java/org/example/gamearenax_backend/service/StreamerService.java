package org.example.gamearenax_backend.service;

import org.example.gamearenax_backend.dto.StreamerDTO;
import org.example.gamearenax_backend.entity.User;

public interface StreamerService {
    int addStreamer(StreamerDTO streamerDTO, User user);
}
