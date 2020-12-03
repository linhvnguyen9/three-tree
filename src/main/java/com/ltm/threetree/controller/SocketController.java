package com.ltm.threetree.controller;

import com.ltm.threetree.entity.Connection;
import com.ltm.threetree.socket.SocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@Slf4j
public class SocketController {

    private final SocketClient socketClient;

    @Autowired
    public SocketController(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    @PostMapping("/join-room")
    public ResponseEntity<?> connectRoom(@RequestBody Connection connection){
        Connection newConnection = socketClient.sendMessage(connection);
        log.info("====Connecting====");
        if (Objects.nonNull(newConnection)){
            return ResponseEntity.ok(newConnection);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
        }
    }
}
