package com.ltm.threetree.service.impl;

import com.ltm.threetree.entity.Room;
import com.ltm.threetree.repository.RoomRepository;
import com.ltm.threetree.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room addRoom(Room room) {
        Room created = Room.builder()
                .minBet(room.getMinBet())
                .serverPort(room.getServerPort())
                .build();
        created = roomRepository.save(created);
        return created;
    }

    @Override
    public Room findRoomByPort(int port) {
        Room room = roomRepository.findRoomByServerPort(port).orElse(new Room());
        return room;
    }

    @Override
    public List<Room> findAllRoom() {
        List<Room> rooms = roomRepository.findAll();
        return rooms;
    }

    @Override
    public Room deleteRoom(int port) {
        Room delete = findRoomByPort(port);
        if (Objects.nonNull(delete)) {
            roomRepository.deleteRoomByServerPort(port);
            return delete;
        } else
            return null;
    }
}
