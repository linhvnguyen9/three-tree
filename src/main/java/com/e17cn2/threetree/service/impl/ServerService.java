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
import java.util.*;

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

    public Round setRound(List<String> listPlayerId){
        Round newRound = new Round();
        List<PlayerRound> playerRounds = new ArrayList<>();
        List<Card> cards = returnCards(listPlayerId.size());
        int index = 0;

        for (String playerId : listPlayerId){
            Player player = userService.findPlayerById(playerId);
            PlayerRound playerRound = new PlayerRound();
            playerRound.setPlayer(player);
            playerRound.setCard1(cards.get(index));
            playerRound.setCard2(cards.get(index + 1));
            playerRound.setCard3(cards.get(index + 2));

            index = index + 3;
            playerRounds.add(playerRound);
            System.out.println(playerRound.toString());
        }
        newRound.setPlayerRoundList(playerRounds);
        Player player = findWinner(playerRounds);
        newRound.setWinner(player);
        System.out.println("WINN: " + player);

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

    public List<Card> returnCards(int numberOfElements){
        List<Card> cards = new ArrayList<>();
        List<String> suiteCards = new ArrayList<>();
        for(int i=1; i < 10; i++){
            for(SuiteCard suiteCard: SuiteCard.values()){
                suiteCards.add(i + "-" + suiteCard);
            }
        }

        for (String cardString : getRandomElement(suiteCards, numberOfElements * 3)){
            Card card = new Card();
            String[] strs = cardString.split("-");
            card.setValue(Integer.parseInt(strs[0]));
            card.setSuiteCard(returnSuite(strs[1]));
            cards.add(card);
        }
        return cards;
    }

    private SuiteCard returnSuite(String str){
        for(SuiteCard suite: SuiteCard.values()){
            if(suite.name().equalsIgnoreCase(str)){
                return suite;
            }
        }
        return null;
    }

    public List<String> getRandomElement(List<String> list,
                                          int totalItems)
    {
        Random rand = new Random();
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return newList;
    }

    public Player findWinner(List<PlayerRound> playerRounds){
        List<Point> points = new ArrayList<>();
        if (Objects.nonNull(playerRounds)){
            for (PlayerRound playerRound:playerRounds) {
                Point point = new Point();
                Card card1 = playerRound.getCard1();
                Card card2 = playerRound.getCard2();
                Card card3 = playerRound.getCard3();
                int totalPoint = card1.getValue() + card2.getValue() + card3.getValue();
                Card maxCard = checkCard(card1, card2, card3);

                point.setUserId(playerRound.getPlayer().getId());
                point.setTotalPoint(getPoint(totalPoint));
                point.setCard(maxCard);
                points.add(point);
            }
        }

        return returnPlayerFromMaxCard(points);
    }

    private int getPoint(int totalPoint){
        if (totalPoint % 10 == 0){
            return 10;
        }else return totalPoint % 10;
    }

    private Player returnPlayerFromMaxCard(List<Point> points){
        Point maxPoint = new Point();
        for (int i = 0; i < points.size() - 1; i++){
            if (points.get(i).getTotalPoint() > points.get(i + 1).getTotalPoint()){
                maxPoint = points.get(i);
            }else maxPoint = points.get(i + 1);

            if (points.get(i).getTotalPoint() == points.get(i + 1).getTotalPoint()){
                Card maxCard = checkCard(points.get(i).getCard(), points.get(i + 1).getCard(), null);
                if (maxCard == points.get(i).getCard()){
                    maxPoint = points.get(i);
                }else maxPoint = points.get(i + 1);
            }
        }

        return userService.findPlayerById(maxPoint.getUserId());
    }

    public Card checkCard(Card card1, Card card2, Card card3){
        Card maxCard = new Card();
        Card OneDiamond = new Card(DIAMONDS, 1);

        if (card1 != null && card2 != null){
            if (OneDiamond == card1){
                maxCard = card1;
            } else if (OneDiamond == card2){
                maxCard = card2;
            } else {
                if (card1.getSuiteCard().getNumVal() > card1.getSuiteCard().getNumVal()){
                    maxCard = card1;
                }else maxCard = card2;

                if (card1.getSuiteCard().getNumVal() == card1.getSuiteCard().getNumVal()){
                    if (card1.getValue() > card2.getValue()){
                        maxCard = card1;
                    }else maxCard = card2;
                }
            }
        }

        if (card3 != null){
            if (card3 == OneDiamond){
                maxCard = card3;
            }else {
                if (maxCard.getSuiteCard().getNumVal() < card3.getSuiteCard().getNumVal()){
                    maxCard = card3;
                }

                if (maxCard.getSuiteCard().getNumVal() == card3.getSuiteCard().getNumVal()){
                    if (maxCard.getValue() < card3.getValue()){
                        maxCard = card3;
                    }
                }
            }
        }

        return maxCard;
    }

}
