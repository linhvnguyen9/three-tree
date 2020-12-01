package com.ltm.threetree.service;

import com.ltm.threetree.entity.Player;

import java.util.List;

public interface UserService {
    Player addPlayer(Player player);

    Player editPlayer(Player player);

    Player deletePlayer(String id);

    Player findPlayerById(String id);

    List<Player> findAllPlayerById(String id);
}
