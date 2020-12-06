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
public class SocketJoinThread extends Thread implements Runnable {
    List<String> listPlayerId;
    ObjectInputStream readFromClient;
    ObjectOutputStream outToClient;
    int countPlayers;
    ThreadCallBack callback;
    boolean checkPlayer;

    @SneakyThrows
    @Override
    public void run() {
        log.info("==Accepting request from a client==");
        Connection connection = (Connection) readFromClient.readObject();
        if (checkJoinConnection(connection)){
            System.out.println(connection.toString());
            listPlayerId.add(connection.getPlayerId());
        }

        if (connection.getMessage().equals("JOIN")){
            checkPlayer = true;
        }

        if(checkPlayer){
            callback.returnNewListPlayer(connection);
            checkPlayer = false;
        }
    }

    private boolean checkJoinConnection(Connection connection){
        return connection.getMessage().equals("JOIN");
    }
}
