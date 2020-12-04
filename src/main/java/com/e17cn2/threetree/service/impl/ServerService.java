package com.e17cn2.threetree.service.impl;

import com.e17cn2.threetree.entity.*;
import com.e17cn2.threetree.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.e17cn2.threetree.entity.SuiteCard.*;

@Component
@Slf4j
public class ServerService {
    @Autowired
    private UserService userService;

    @SneakyThrows
    public void newSocketServer(Socket connectionSocket, int countPlayer) {
        log.info("====create new server=====");

        try {
            log.info("==Accepting request from a client==");

            ObjectInputStream readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient =
                    new ObjectOutputStream(connectionSocket.getOutputStream());
            Connection connection = (Connection) readFromClient.readObject();

            log.info("====Connect success server==== ");
            log.info("=======" + connection.toString());

            setSocket(connection, countPlayer);
            log.info(connection.toString());
            outToClient.writeObject(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public void returnCard(Socket connectionSocket, int countPlayer){
        int countReady = 0;
        ObjectInputStream readFromClient;
        ObjectOutputStream outToClient;

        while (true) {
            countReady++;
            log.info("===PLAYER READY===");

            readFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            Connection connection = (Connection) readFromClient.readObject();

            log.info("=======" + connection.toString());

            if (countReady >= countPlayer / 2){
                Round round = setRound(connection, countReady);
                log.info(round.toString());
                outToClient.writeObject(round);
            }
        }
    }

    private Round setRound(Connection connection, int countPlayer){
        Player player1 = userService.findPlayerById("5fc679f37668b97b7039e2aa");
        Player player2 = userService.findPlayerById("5fc9a417dc13dd0fc1cc3be4");
        Round newRound = new Round();
//        for (int i = 0; i <= countPlayer; i++){
//            PlayerRound playerRound = new PlayerRound();
//        }

        List<PlayerRound> playerRounds = new ArrayList<>();

        PlayerRound playerRound1 = new PlayerRound();
        playerRound1.setPlayer(player1);
        playerRound1.setCard1(new Card(HEARTS, 2));
        playerRound1.setCard2(new Card(DIAMONDS, 5));
        playerRound1.setCard3(new Card(SPADE, 8));

        PlayerRound playerRound2 = new PlayerRound();
        playerRound2.setPlayer(player2);
        playerRound2.setCard1(new Card(SPADE, 6));
        playerRound2.setCard2(new Card(DIAMONDS, 9));
        playerRound2.setCard3(new Card(SPADE, 3));

        playerRounds.add(playerRound1);
        playerRounds.add(playerRound2);
        newRound.setPlayerRoundList(playerRounds);
        newRound.setWinner(player2);

        return newRound;
    }

    private void setSocket(Connection connection, int count){
        InetAddress ip;
        String hostAddress;
        try {
            if (count >= 0 && count <= 4 && connection.getMessage().equals("JOIN")){
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
