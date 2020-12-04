package com.e17cn2.threetree.util.common;

import com.e17cn2.threetree.service.impl.ServerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.Socket;

@AllArgsConstructor
@NoArgsConstructor
public class SocketThread extends Thread implements Runnable{
    private ServerService serverService;
    private Socket connectionSocket;
    int countPlayers;

    @SneakyThrows
    @Override
    public void run() {
        serverService.newSocketServer(connectionSocket, countPlayers);
    }
}
