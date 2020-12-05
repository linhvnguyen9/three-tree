package com.e17cn2.threetree;

import com.e17cn2.threetree.entity.Card;
import com.e17cn2.threetree.service.impl.ServerService;
import com.e17cn2.threetree.util.common.SocketThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

@SuppressWarnings("InfiniteLoopStatement")
@SpringBootApplication
@Slf4j
public class ThreeTreeApplication implements CommandLineRunner{

  @Autowired
  private ServerService serverService;

  public static void main(String[] args) {
    SpringApplication.run(ThreeTreeApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//    ServerSocket serverSocket = new ServerSocket(8090);
//    int countPlayers = 0;
//    Socket connectionSocket;
//    while (true){
//      try{
//        connectionSocket = serverSocket.accept();
//        ObjectInputStream readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
//        ObjectOutputStream outToClient =
//            new ObjectOutputStream(connectionSocket.getOutputStream());
//        countPlayers++;
//        SocketThread socketThread =
//            new SocketThread(serverService, readFromClient, outToClient, countPlayers);
//        socketThread.start();
//      }catch (SocketException e){
//        log.warn(e.toString());
//      }
//    }
      List<Card> cards = serverService.returnCards();
      for (Card card : cards){
        System.out.println(card.toString());
      }
  }
}
