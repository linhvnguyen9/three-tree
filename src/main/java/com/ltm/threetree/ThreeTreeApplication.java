package com.ltm.threetree;

import com.ltm.threetree.socket.SocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ThreeTreeApplication implements CommandLineRunner{

  public static void main(String[] args) {
    SpringApplication.run(ThreeTreeApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    SocketServer socketServer = new SocketServer();
    socketServer.newSocketServer();
  }
}
