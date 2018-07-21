package ticket.com.tickettoridegames;

import org.junit.Test;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.model.User;
import ticket.com.tickettoridegames.utility.web.Result;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameServiceTest {
    private String userId;
    private String userId2;
    private String gameId;

    private Poller poller;

    @Test
    public void testInitToGameplay() {
        initToGameplay();
        assertTrue(userId != null && userId2 != null && gameId != null);
        assertTrue(ClientModel.get_instance().getMyActiveGame() != null);
        assertTrue(ClientModel.get_instance().getMyPlayer() != null);
        poller.stop();
    }

    @Test
    public void testInitGame() {
        GamePlayService service = new GamePlayService();
        initToGameplay();

        service.initGame(gameId);
        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(ClientModel.get_instance().getMyActiveGame().getTurnOrder().size(), 2); //turn order
//        assertEquals(ClientModel.get_instance().getMyActiveGame().getPlayersColors().size(), 2); //colors
//        assertTrue(ClientModel.get_instance().getMyPlayer().getColor() != null);
        assertEquals(ClientModel.get_instance().getMyPlayer().getTrainCards().size(), 4);
    }

    @Test
    public void testPhase2Simple() {
        initToGameplay();
        GamePlayService proxy = new GamePlayService();

        //init
        proxy.initGame(gameId);
        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(ClientModel.get_instance().getMyActiveGame().getTurnOrder().size(), 2); //check turns
        assertEquals(ClientModel.get_instance().getMyActiveGame().getPlayersColors().size(), 2);//check colors
        assertEquals(ClientModel.get_instance().getMyPlayer().getTrainCards().size(), 4);//check hand
        assertEquals(ClientModel.get_instance().getMyPlayer().getDestinationCards().size(), 3);//check destination cards

        //return a destination
//        DestinationCard returnedCard = ClientModel.get_instance().getMyPlayer().getDestinationCards().
//        proxy.returnDestinationCard(gameId, );

        //draw a card


    }

    @Test
    public void testDrawTrainCard() {
        //prepare
        initToGameplay();
        GamePlayService gamePlayService = new GamePlayService();


        //you draw
        gamePlayService.drawTrainCard(userId, gameId);
        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        //other player draws
//        //remove null top card
//        gamePlayService.drawTrainCard(userId2, gameId);
//        try {
//            Thread.sleep(3000); //wait for poller
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Map<String, Integer> countsOfCards = ClientModel.get_instance().getCountsOfCards();
//        assertEquals(countsOfCards.get(userId2), (Integer) 1); //check for visual
    }

    private void initToGameplay() {
        new UtilityService().clearServer();

        //new users
        User user = new User("username", "password");
        try {
            Result registerResult = LoginService.class.newInstance().register(user);
            userId = registerResult.getMessage();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        User user2 = new User("username2", "password2");
        try {
            Result registerResult = LoginService.class.newInstance().register(user2);
            userId2 = registerResult.getMessage();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        poller = new Poller(); //get it going

        //new game
        Result joinResult = JoinService.createGame(userId, "gameName", 2);
        gameId = joinResult.getMessage();

        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //new players
        Player player1 = new Player(user.getUsername(), user.getId());
        JoinService.addPlayer2(gameId, userId);
        Player player2 = new Player(user2.getUsername(), user2.getId());
        JoinService.addPlayer2(gameId, userId2);

        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
