package com.e17cn2.threetree.controller;

import com.e17cn2.threetree.entity.Connection;
import com.e17cn2.threetree.entity.Round;
import com.e17cn2.threetree.service.impl.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@Slf4j
public class SocketController {

    private final ClientService clientService;

    @Autowired
    public SocketController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/join-room/{room_id}")
    public ResponseEntity<?> connectRoom(@RequestBody Connection connection,
                                         @PathVariable("room_id") int roomId){
        connection.setRoomId(roomId);
        Connection newConnection = clientService.joinRoomRequest(connection);
        log.info("====Connecting====");
        if (Objects.nonNull(newConnection)){
            return ResponseEntity.ok(newConnection);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/play/{room_id}")
    public ResponseEntity<?> playGame(@RequestBody Connection connection,
                                         @PathVariable("room_id") int roomId){
        connection.setRoomId(roomId);
        Round round = clientService.playGame(connection);
        log.info("====Connecting====");
        if (Objects.nonNull(round)){
            return ResponseEntity.ok(round);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
        }
    }

}
