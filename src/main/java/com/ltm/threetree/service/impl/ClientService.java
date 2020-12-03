package com.ltm.threetree.service.impl;

import com.ltm.threetree.entity.Connection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Service
@Slf4j
public class ClientService {

    @SneakyThrows
    public Connection joinRoomRequest(Connection connection){
        Socket clientSocket = new Socket("127.0.0.1", connection.getRoomId());

        ObjectOutputStream outToServer =
                new ObjectOutputStream(clientSocket.getOutputStream());

        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        outToServer.writeObject(connection);
        log.info("====" + connection.toString());

        Connection readMessage = (Connection) inFromServer.readObject();
        log.info("=====Successfully=====");
        return readMessage;
    }
}
