package com.ltm.threetree.service;

import com.ltm.threetree.entity.Room;

import java.util.List;

public interface RoomService {
    Room addRoom(Room room);

    Room findRoomByPort(int port);

    List<Room> findAllRoom();

    Room deleteRoom(int port);


}
