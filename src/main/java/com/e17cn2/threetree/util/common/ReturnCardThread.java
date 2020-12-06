package com.e17cn2.threetree.util.common;

import com.e17cn2.threetree.entity.Connection;
import com.e17cn2.threetree.util.ThreadCallBack;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ReturnCardThread extends Thread implements Runnable{
  List<String> listPlayerId;
  ObjectInputStream inputStream;
  int countPlayers;
  ThreadCallBack callback;

  @SneakyThrows
  @Override
  public void run() {
    log.info("==Accepting request from a client==");
    Connection connection = (Connection) inputStream.readObject();
    log.info("CARD: " + connection.toString());
    if (checkReady(connection)){
      callback.checkDealCards(listPlayerId);
    }
  }

  private boolean checkReady(Connection connection){
    return connection.getMessage().equals("READY");
  }
}
