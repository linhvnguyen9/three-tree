package com.e17cn2.threetree.service.impl;

import com.e17cn2.threetree.entity.*;
import com.e17cn2.threetree.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
    public void newSocketServer(Connection connection,
                                ObjectOutputStream outToClient, int countPlayer) {

        try {
            log.info("====Connect success server with player id =  " + connection.getPlayerId());
            log.info("=======" + connection.toString());

            setSocket(connection, countPlayer);
            log.info(connection.toString());
            outToClient.writeObject(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    @SneakyThrows
//    public void returnCard(Connection connection,
//                           ObjectOutputStream outToClient, int countPlayer){
//        try {
//            log.info("===PLAYER READY " + connection.getPlayerId());
//            log.info("=======" + connection.toString());
//
//            if (countPlayer >=  2){
//                Round round = setRound();
//                log.info(round.toString());
//                log.info("=======WAIT FOR RETURN CARD=====");
//                outToClient.writeObject(round);
//                log.info("====RETURN SUCCESS : " + connection.getPlayerId());
//            }
//        }catch (SocketException e){
//            log.debug(e.getMessage());
//        }
//    }

    public Round setRound(List<String> listPlayerId){
        Round newRound = new Round();
        List<PlayerRound> playerRounds = new ArrayList<>();

        for (String playerId : listPlayerId){
            Player player = userService.findPlayerById(playerId);
            PlayerRound playerRound = new PlayerRound();
//            playerRound.setPlayer(player);
//            playerRound.setCard1(new Card(HEARTS, 2));
//            playerRound.setCard2(new Card(DIAMONDS, 5));
//            playerRound.setCard3(new Card(SPADE, 8));

            playerRounds.add(playerRound);
        }
        newRound.setPlayerRoundList(playerRounds);

        return newRound;
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
