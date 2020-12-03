package com.ltm.threetree;

import com.ltm.threetree.service.impl.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ThreeTreeApplication implements CommandLineRunner{

  public static void main(String[] args) {
    SpringApplication.run(ThreeTreeApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    ServerService serverService = new ServerService();
    serverService.newSocketServer();
  }
}
