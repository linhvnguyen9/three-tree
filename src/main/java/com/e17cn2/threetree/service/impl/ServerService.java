package com.e17cn2.threetree.service.impl;

import com.e17cn2.threetree.entity.Connection;
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
        int count = 0;
        ServerSocket serverSocket = new ServerSocket(8090);

        log.info("====create new server=====");

        while (true) {
            Socket connectionSocket = serverSocket.accept();
            count++;
            log.info("==Accepting request from a client==");

            ObjectInputStream readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient =
                    new ObjectOutputStream(connectionSocket.getOutputStream());
            Connection connection = (Connection) readFromClient.readObject();

            log.info("====Connect success server==== ");
            log.info("=======" + connection.toString());

            setSocket(connection, count);
            log.info(connection.toString());
            outToClient.writeObject(connection);
        }
    }

    private void setSocket(Connection connection, int count){
        InetAddress ip;
        String hostAddress;
        try {
            if (count >= 0 && count <= 4){
                ip = InetAddress.getLocalHost();
                hostAddress = ip.getHostAddress();
                log.info("=====IP:" + hostAddress);
                connection.setIpAddress(hostAddress);
                connection.setMessage("Success " + count);
            }else {
                connection.setMessage("Full slot");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
