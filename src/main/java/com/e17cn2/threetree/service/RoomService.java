package com.e17cn2.threetree.service;

import com.e17cn2.threetree.entity.Room;

import java.util.List;

public interface RoomService {
    Room addRoom(Room room);

    Room findRoomByPort(int port);

    List<Room> findAllRoom();

    Room deleteRoom(int port);


}
