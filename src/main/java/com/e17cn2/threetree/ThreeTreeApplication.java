package com.e17cn2.threetree;

import com.e17cn2.threetree.entity.Card;
import com.e17cn2.threetree.entity.Connection;
import com.e17cn2.threetree.entity.Round;
import com.e17cn2.threetree.service.impl.ServerService;
import com.e17cn2.threetree.util.ReturnCardCallback;
import com.e17cn2.threetree.util.ThreadCallBack;
import com.e17cn2.threetree.util.common.SocketThread;
import lombok.SneakyThrows;
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
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("InfiniteLoopStatement")
@SpringBootApplication
@Slf4j
public class ThreeTreeApplication implements CommandLineRunner, ThreadCallBack, ReturnCardCallback {

  @Autowired
  private ServerService serverService;

  public static void main(String[] args) {
    SpringApplication.run(ThreeTreeApplication.class, args);
  }

  int countPlayers = 0;
  List<ObjectInputStream> ois = new ArrayList<>();
  List<ObjectOutputStream> oos = new ArrayList<>();
  List<String> listPlayerId = new ArrayList<>();
  List<Connection> connections = new ArrayList();
  boolean checkPlayer = false;

  @Override
  public void run(String... args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(8090);
    Socket connectionSocket;

    while (true){
      try{
        connectionSocket = serverSocket.accept();
        ObjectInputStream readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
        ObjectOutputStream outToClient =
            new ObjectOutputStream(connectionSocket.getOutputStream());
        ois.add(readFromClient);
        oos.add(outToClient);
        countPlayers++;
        SocketThread socketThread =
            new SocketThread(listPlayerId, serverService, readFromClient, outToClient, countPlayers, this, checkPlayer);
        socketThread.start();
      }catch (SocketException e){
        log.warn(e.toString());
      }
    }
  }

  private boolean shouldDealCards() {
    return countPlayers >= 2;
  }

  @SneakyThrows
  private void dealCards(List<ObjectOutputStream> oos, List<String> listPlayerId) {
    Round round = serverService.setRound(listPlayerId);
    for (ObjectOutputStream stream: oos) {
      stream.writeObject(round);
    }
  }

  @Override
  public void checkDealCards(List<String> listPlayerId) {
    if (shouldDealCards()) {
      dealCards(oos, listPlayerId);
    }
  }

  @Override
  public void checkReturnCard() {

  }

  @SneakyThrows
  @Override
  public void returnNewListPlayer(Connection connection,
                                  ObjectOutputStream outToClient, List<String> listPlayerId) {
    for (String playerId : listPlayerId){
      try {
        log.info("=======NEW PLAYER: " + connection.toString());
        log.info(connection.toString());
        connection.setMessage("SUCCESS");
        connection.setRoomId(8090);
        connection.setPlayerId(playerId);
        connections.add(connection);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    outToClient.writeObject(connections);
  }
}
