package com.ltm.threetree.service.impl;

import com.ltm.threetree.entity.Connection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

@Component
@Slf4j
public class ServerService {

    @SneakyThrows
    public void newSocketServer() {
        ServerSocket serverSocket = new ServerSocket(8090);

        log.info("====create new server=====");
        Socket connectionSocket = serverSocket.accept();
        log.info("==Accepting request from a client==");

        while (true) {
            ObjectInputStream readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient =
                    new ObjectOutputStream(connectionSocket.getOutputStream());
            Connection connection = (Connection) readFromClient.readObject();

            log.info("====Connect success server==== ");
            log.info("=======" + connection.toString());

            setSocket(connection);
            log.info(connection.toString());
            outToClient.writeObject(connection);
        }
    }

    private void setSocket(Connection connection){
        InetAddress ip;
        String hostAddress;
        try {
            ip = InetAddress.getLocalHost();
            hostAddress = ip.getHostAddress();
            log.info("=====IP:" + hostAddress);
            connection.setIpAddress(hostAddress);
            connection.setMessage("Success");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
