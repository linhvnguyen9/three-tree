package com.e17cn2.threetree;

import com.e17cn2.threetree.entity.Connection;
import com.e17cn2.threetree.entity.Round;
import com.e17cn2.threetree.service.impl.ServerService;
import com.e17cn2.threetree.util.ReturnCardCallback;
import com.e17cn2.threetree.util.ThreadCallBack;
import com.e17cn2.threetree.util.common.ReturnCardThread;
import com.e17cn2.threetree.util.common.SocketJoinThread;
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
  List<ObjectInputStream> ois2 = new ArrayList<>();
  List<ObjectOutputStream> oos2 = new ArrayList<>();
  List<String> listPlayerId = new ArrayList<>();
  List<Connection> connections = new ArrayList();
  boolean checkPlayer = false;
  Socket connectionSocket;
  Socket connectionSocket2;

  @Override
  public void run(String... args) throws Exception {
    ServerSocket serverSocket1 = new ServerSocket(8090);
    ServerSocket serverSocket2 = new ServerSocket(8091);

    while (true){
      try{
        connectionSocket = serverSocket1.accept();
        connectionSocket2 = serverSocket2.accept();
        ObjectInputStream readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
        ObjectOutputStream outToClient =
            new ObjectOutputStream(connectionSocket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(connectionSocket2.getInputStream());
        ObjectOutputStream outputStream = new ObjectOutputStream(connectionSocket2.getOutputStream());
        ois.add(readFromClient);
        oos.add(outToClient);
        ois2.add(inputStream);
        oos2.add(outputStream);
        countPlayers++;
        SocketJoinThread socketJoinThread =
            new SocketJoinThread(listPlayerId, readFromClient, outToClient, countPlayers, this, checkPlayer);
        ReturnCardThread returnCardThread
            = new ReturnCardThread(listPlayerId, inputStream, countPlayers, this);
        socketJoinThread.start();
        returnCardThread.start();
      }catch (SocketException e){
        log.warn(e.toString());
      }
    }
  }

  private boolean shouldDealCards() {
    return countPlayers >= 2;
  }

  @SneakyThrows
  private void dealCards(List<String> listPlayerId) {
    this.listPlayerId = listPlayerId;
    Round round = serverService.setRound(listPlayerId);
    for (ObjectOutputStream stream: oos2) {
      stream.writeObject(round);
    }
  }

  @Override
  public void checkDealCards(List<String> listPlayerId) {
    if (shouldDealCards()) {
      dealCards(listPlayerId);
    }
  }

  @Override
  public void checkReturnCard() {

  }

  @SneakyThrows
  @Override
  public void returnNewListPlayer(Connection connection) {

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

    for (ObjectOutputStream stream: oos) {
      stream.writeObject(connections);
    }
  }
}
