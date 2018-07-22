package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.List;

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
    private String otherId;
    private String userId;
    private String gameId;

    private Poller poller;

    @Test
    public void testInitToGameplay() {
        initToGameplay();
        assertTrue(otherId != null && userId != null && gameId != null);
        assertTrue(ClientModel.get_instance().getMyActiveGame() != null);
        assertTrue(ClientModel.get_instance().getMyPlayer() != null);
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
        assertEquals(ClientModel.get_instance().getMyActiveGame().getPlayersColors().size(), 2); //colors
        assertTrue(ClientModel.get_instance().getMyPlayer().getColor() != null);
        assertEquals(ClientModel.get_instance().getMyPlayer().getTrainCards().size(), 4);
    }

    @Test
    public void testDrawTrainCard() {
        //prepare
        initToGameplay();
        GamePlayService service = new GamePlayService();
        service.initGame(gameId);

        //you draw
        service.drawTrainCard(userId, gameId);
        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4+1, ClientModel.get_instance().getMyPlayer().getTrainCards().size());

//        //other player draws
//        //remove null top card
//        gamePlayService.drawTrainCard(userId, gameId);
//        try {
//            Thread.sleep(3000); //wait for poller
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Map<String, Integer> countsOfCards = ClientModel.get_instance().getCountsOfCards();
//        assertEquals(countsOfCards.get(userId), (Integer) 1); //check for visual
    }

    @Test
    public void testTrainBank() {
        initToGameplay();
        GamePlayService service = new GamePlayService();
        service.initGame(gameId);
        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(ClientModel.get_instance().getMyActiveGame().getTrainBank().size(), 5); //initialized

        List<TrainCard> prevBank = ClientModel.get_instance().getMyActiveGame().getTrainBank();
        System.out.println("prev0: " + prevBank.get(0).getType());
        service.pickupTrainCard(userId, gameId, 0);
        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(ClientModel.get_instance().getMyPlayer().getTrainCards().get(4), prevBank.get(0)); //in hand

        List<TrainCard> curBank = ClientModel.get_instance().getMyActiveGame().getTrainBank(); //rest untouched
        assertTrue(prevBank.get(1).getType() == curBank.get(1).getType()
                && prevBank.get(2).getType() == curBank.get(2).getType()
                && prevBank.get(3).getType() == curBank.get(3).getType()
                && prevBank.get(4).getType() == curBank.get(4).getType()
        );

        System.out.println("prev: " + prevBank.get(0).getType() + " cur: " + curBank.get(0).getType()); //replaced
    }

    private void initToGameplay() {
        new UtilityService().clearServer();

        //new users
        User otherPlayer = new User("username", "password");
        try {
            Result registerResult = LoginService.class.newInstance().register(otherPlayer);
            otherId = registerResult.getMessage();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        User user = new User("username2", "password2"); //register auto logs in //so it resets user
        try {
            Result registerResult = LoginService.class.newInstance().register(user);
            userId = registerResult.getMessage();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        poller = new Poller(); //get it going

        //new game
        Result joinResult = JoinService.createGame(otherId, "gameName", 2);
        gameId = joinResult.getMessage();

        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //new players
        Player player1 = new Player(otherPlayer.getUsername(), otherPlayer.getId());
        JoinService.addPlayer2(gameId, otherId);
        Player player2 = new Player(user.getUsername(), user.getId());
        JoinService.addPlayer2(gameId, userId);

        try {
            Thread.sleep(3000); //wait for poller
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
