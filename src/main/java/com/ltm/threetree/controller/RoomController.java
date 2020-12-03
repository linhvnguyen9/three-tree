package com.ltm.threetree.controller;

import com.ltm.threetree.entity.Room;
import com.ltm.threetree.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/rooms")
@Slf4j
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody Room room){
        Room created = roomService.addRoom(room);
        if(Objects.isNull(created)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            log.info("=====create - ọc kê======");
            return new ResponseEntity<>(created,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{port}")
    public ResponseEntity<?> getRoomByServerPort(@PathVariable("port") int port){
        Room room = roomService.findRoomByPort(port);
        if(Objects.isNull(room)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            log.info("=====getRoomByPort - ọc kê ======");
            return new ResponseEntity<>(room,HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<?> getRoomAll() {
        List<Room> rooms = roomService.findAllRoom();
        if (Objects.isNull(rooms)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("=====getAll - ọc kê======");
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{port}")
    public ResponseEntity<?> deleteRoom(@PathVariable("port") int port){
        Room delete = roomService.deleteRoom(port);
        if (Objects.isNull(delete)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("=====delete - ọc kê======");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
