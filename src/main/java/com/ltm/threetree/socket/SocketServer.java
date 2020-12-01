package com.ltm.threetree.socket;

import com.ltm.threetree.entity.Connection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

@Component
@Slf4j
public class SocketServer {

    @Value("${app.ip_address}")
    private String ipAddress;

    @SneakyThrows
    public void newSocketServer() {
        ServerSocket serverSocket = new ServerSocket(8090);

        log.info("====create new server=====");
        Socket connectionSocket = serverSocket.accept();

        while (true){
            ObjectInputStream readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            Connection connection = (Connection) readFromClient.readObject();

            log.info("====Connect success server==== ");
            log.info("=======" + connection.toString());

            setSocket(connection);
            ObjectOutputStream outToClient =
                    new ObjectOutputStream(connectionSocket.getOutputStream());
            log.info(connection.toString());
            outToClient.writeBytes(connection.toString());
        }
    }

    private void setSocket(Connection connection){
        InetAddress ip;
        String hostAddress;
        try {
            ip = InetAddress.getLocalHost();
            hostAddress = ip.getHostAddress();
            connection.setIpAddress(hostAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
