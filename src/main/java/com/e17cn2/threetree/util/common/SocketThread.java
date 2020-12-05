package com.e17cn2.threetree.util.common;

import com.e17cn2.threetree.entity.Connection;
import com.e17cn2.threetree.service.impl.ServerService;
import com.e17cn2.threetree.util.ThreadCallBack;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SocketThread extends Thread implements Runnable {
    List<String> listPlayerId;
    private ServerService serverService;
    ObjectInputStream readFromClient;
    ObjectOutputStream outToClient;
    int countPlayers;
    ThreadCallBack callback;

    @SneakyThrows
    @Override
    public void run() {
        log.info("==Accepting request from a client==");
        Connection connection = (Connection) readFromClient.readObject();
        if (checkJoinConnection(connection)){
            listPlayerId.add(connection.getPlayerId());
            serverService.newSocketServer(connection, outToClient, countPlayers);
        }

        connection = (Connection) readFromClient.readObject();
        if (checkReady(connection)){
            callback.checkDealCards(listPlayerId);
        }
    }

    private boolean checkJoinConnection(Connection connection){
        return connection.getMessage().equals("JOIN");
    }

    private boolean checkReady(Connection connection){
        return connection.getMessage().equals("READY");
    }
}
