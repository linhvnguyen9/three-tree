package com.e17cn2.threetree;

import com.e17cn2.threetree.entity.Connection;
import com.e17cn2.threetree.service.impl.ServerService;
import com.e17cn2.threetree.util.common.SocketThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
@Slf4j
public class ThreeTreeApplication implements CommandLineRunner{

  public static void main(String[] args) {
    SpringApplication.run(ThreeTreeApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(8090);
    ServerService serverService;
    int countPlayers = 0;
    Socket connectionSocket;
    while (true){
      connectionSocket = serverSocket.accept();
      countPlayers++;
      serverService = new ServerService();
      SocketThread socketThread = new SocketThread(serverService, connectionSocket, countPlayers);
      socketThread.start();

      log.info("====================================");
//      if (countPlayers >= 2){
//        serverService.returnCard(connectionSocket, countPlayers);
//      }
    }
  }
}
