package ticket.com.tickettoridegames.model;

import org.junit.Test;

import java.util.Map;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ClientModelTest {
    String userId = "userId";
    String userId2 = "userId2";
    String gameId = "gameId";

    @Test
    public void testInitToGamePlay() {
        initToGamePlay();
        assertTrue(true);
    }

    @Test
    public void testGetPoints() {
        initToGamePlay();

        Integer points = 4;
        ClientModel.get_instance().getMyPlayer().setPoints(points);
        Map<String, Integer> allPoints = ClientModel.get_instance().getMyActiveGame().getCountsOfPoints();

        assertEquals(allPoints.get(userId), points);
    }

//    @Test
//    public void testPhase2Simple() {
//        ClientModel clientModel = ClientModel.get_instance();
//        initGame();
//
//        //mock pick some destination cards
//        clientModel.addDestinationCard(
//                new DestinationCard(new City("Saltlake"), 5),
//                new DestinationCard(new City("Colorado"), 5)
//        );
//        assertEquals(clientModel.getDestinationCards().size(), 2);
//
//        //wait for turn
//        while(!clientModel.isMyTurn()) { clientModel.switchTurn(); }
//        do { //and another time around
//            clientModel.switchTurn();
//        } while(!clientModel.isMyTurn());
//
//        //get my hand at beginning
//        List<TrainCard> hand = clientModel.getMyHand();
//        assertEquals(hand.size(), 4);
//
//        //claim a route
//
//        //get claimed routes
//
//        //get points
//
//        //get my trainsCount
//
//        //get myHand again
//
//        //get Counts of cards
//
//        //draw a card
//        TrainCard card1 = clientModel.getDeckTop();
//        TrainCard card2 = clientModel.drawATrainCard();
//        TrainCard card3 = clientModel.getDeckTop();
//        assertEquals(card1, card2);
//        assertNotEquals(card1, card3);
//    }

    private void initToGamePlay() {
        //user
        User user = new User();
        user.setId(userId);
        ClientModel.get_instance().setUser(user);

        //game
        Game game = new Game();
        game.setId(gameId);
        game.setMaxPlayers(2);
        ClientModel.get_instance().addGameToList(game);

        //players
        Player player = new Player("", userId);
        ClientModel.get_instance().addPlayerToGame(gameId, player);
        Player player2 = new Player("", userId2);
        ClientModel.get_instance().addPlayerToGame(gameId, player2);

        //init
        ClientModel.get_instance().getMyActiveGame().initGame();
    }
}
