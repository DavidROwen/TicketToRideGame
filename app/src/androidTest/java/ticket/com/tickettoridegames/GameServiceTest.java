package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameServiceTest {
    private String otherId;
    private String userId;
    private String gameId;

    private Poller poller;
    private GamePlayService service;

    @Test
    public void testInitToGameplay() {
        initToGameplay();
        assertTrue(otherId != null && userId != null && gameId != null);
        assertTrue(ClientModel.get_instance().getMyActiveGame() != null);
        assertTrue(ClientModel.get_instance().getMyPlayer() != null);
    }

    @Test
    public void testInitGame() {
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4){}

        assertEquals(ClientModel.get_instance().getMyActiveGame().getTurnOrder().size(), 2); //turn order
        assertEquals(ClientModel.get_instance().getMyActiveGame().getPlayersColors().size(), 2); //colors
        assertTrue(ClientModel.get_instance().getMyPlayer().getColor() != null);
        assertTrue(ClientModel.get_instance().getMyActiveGame().getTrainCardsDeck().size() <= 53);
    }

    @Test
    public void testDrawTrainCard() {
        initToGameplay();

        //prepare
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4){} //initialized

        //test when you draw
        service.drawTrainCard(userId, gameId);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() == 4){} //added here

        //test when others draw
        service.drawTrainCard(otherId, gameId);
        Map<String, Integer> countsOfCards;
        do {
            countsOfCards = ClientModel.get_instance().getMyActiveGame().getCountsOfCardsInHand();
        } while(countsOfCards.get(otherId) != 5); //added there
    }

    @Test
    public void testTrainBank() {
        initToGameplay();

        while(ClientModel.get_instance().getMyActiveGame().getTrainBank().size() != 5){} //initialized

        //prepare
        TrainCard prev0 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(0);
        TrainCard prev1 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(1);
        System.out.println(prev0.getType());

        //run
        service.pickupTrainCard(userId, gameId, 0);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 5){}
        while(ClientModel.get_instance().getMyActiveGame().getTrainBank().get(0) == prev0){}

        //check
        TrainCard cur1 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(1);
        assertEquals(prev1.getType(), cur1.getType()); //rest untouched
        assertEquals(ClientModel.get_instance().getMyPlayer().getTrainCards().get(4), prev0); //in hand

        TrainCard cur0 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(0);
        System.out.println("prev: " + prev0.getType() + " cur: " + cur0.getType()); //replaced
    }

    @Test
    public void testDestinationCards() {
        initToGameplay();

        service.drawDestinationCard(userId, gameId);
        while(ClientModel.get_instance().getMyPlayer().getTempDeck().size() != 3);
        List<DestinationCard> tempDeck = ClientModel.get_instance().getMyPlayer().getTempDeck();

        LinkedList<DestinationCard> claimed = new LinkedList<>();
        claimed.add(tempDeck.get(0));
        claimed.add(tempDeck.get(1));
        service.claimDestinationCard(userId, gameId, claimed);
        while(ClientModel.get_instance().getMyPlayer().getDestinationCards().size() != 2);

        LinkedList<DestinationCard> returning = new LinkedList<>();
        returning.add(tempDeck.get(2));
        Integer prevSize = ClientModel.get_instance().getMyActiveGame().getDestinationCards().size();
        service.returnDestinationCard(gameId, returning);
        while(ClientModel.get_instance().getMyActiveGame().getDestinationCards().size() == prevSize);
    }

    private void initToGameplay() {
        new UtilityService().clearServer();

        //login
        User otherPlayer = new User("username", "password");
        Result registerResult = new LoginService().register(otherPlayer);
        otherId = registerResult.getMessage();

        User user = new User("username2", "password2"); //register auto logs in //so it resets user
        Result registerResult1 = new LoginService().register(user);
        userId = registerResult1.getMessage();

        //poller
        poller = new Poller(); //get it going

        //create game
        Result joinResult = JoinService.createGame(otherId, "gameName", 2);
        gameId = joinResult.getMessage();
        while(ClientModel.get_instance().getGames().isEmpty());

        //join game
        Player player1 = new Player(otherPlayer.getUsername(), otherPlayer.getId());
        JoinService.joinGame(otherId, gameId);

        Player player2 = new Player(user.getUsername(), user.getId());
        JoinService.joinGame(userId, gameId);
        while(ClientModel.get_instance().getMyActiveGame() == null);

        //start game
        LobbyService.startGame(gameId);
        while(!ClientModel.get_instance().isGameStarted(gameId)){}

        //init game
        service = new GamePlayService();
        service.initGame(gameId);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4);
    }
}
