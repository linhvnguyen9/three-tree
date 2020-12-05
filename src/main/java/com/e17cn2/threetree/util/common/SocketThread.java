package com.e17cn2.threetree.util.common;

import com.e17cn2.threetree.entity.Connection;
import com.e17cn2.threetree.service.impl.ServerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SocketThread extends Thread implements Runnable{
    private ServerService serverService;
    ObjectInputStream readFromClient;
    ObjectOutputStream outToClient;
    int countPlayers;

    @SneakyThrows
    @Override
    public void run() {
        log.info("==Accepting request from a client==");
        Connection connection = (Connection) readFromClient.readObject();
        if (checkJoinConnection(connection)){
            serverService.newSocketServer(connection, outToClient, countPlayers);
        }
        log.info("READY: " + connection.toString());
        if (checkReady(connection)){
            serverService.returnCard(connection, outToClient, countPlayers);
        }
    }

    private boolean checkJoinConnection(Connection connection){
        return connection.getMessage().equals("JOIN");
    }

    private boolean checkReady(Connection connection){
        return connection.getMessage().equals("READY");
    }
}
