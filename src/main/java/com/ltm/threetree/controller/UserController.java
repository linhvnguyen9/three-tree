//package com.ltm.threetree.controller;
//
//import com.ltm.threetree.entity.Connection;
//import com.ltm.threetree.socket.SocketServer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Objects;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//    @Autowired
//    SocketServer socketServer;
//
//    @PostMapping("/new-room")
//    public ResponseEntity<?> createNewRoom(@RequestBody Connection connection){
//        Connection newConnection = socketServer.newSocketServer(connection);
//        if (Objects.nonNull(newConnection)){
//            return ResponseEntity.ok(newConnection);
//        }else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
