package org.example.gamearenax_backend.service;

import org.example.gamearenax_backend.dto.ClanDTO;

import java.util.List;

public interface ClanService {
    ClanDTO createClan(ClanDTO clanDTO);

    List<ClanDTO> getAllClans();

    List<ClanDTO> getAllClansByOpen();

    List<ClanDTO> getAllClansByRankingPointsAsc();

}
