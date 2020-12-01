package com.ltm.threetree.service.impl;

import com.ltm.threetree.entity.Player;
import com.ltm.threetree.repository.UserRepository;
import com.ltm.threetree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Player addPlayer(Player player) {
        Player created = Player.builder()
                .username(player.getUsername())
                .password(player.getPassword())
                .playerStatus(player.getPlayerStatus())
                .build();
        created = userRepository.save(created);
        return created;
    }

    @Override
    public Player editPlayer(Player player) {
        Player updated = findPlayerById(player.getId());
        if (Objects.nonNull(updated)){
            updated.setUsername(player.getUsername());
            updated.setPassword(player.getPassword());
            updated.setMoney(player.getMoney());

            userRepository.save(updated);
        }
        return updated;
    }

    @Override
    public Player deletePlayer(String id) {
        Player deleted = findPlayerById(id);
        if (Objects.nonNull(deleted)){
            userRepository.deleteById(deleted.getId());
            return deleted;
        }
        else return null;
    }


    @Override
    public Player findPlayerById(String id) {
        Player player = userRepository.findById(id).orElse(null);
        return player;
    }

    @Override
    public List<Player> findAllPlayerById(String id) {
        List<Player> players = userRepository.findAllById(id);
        return players;
    }
}
