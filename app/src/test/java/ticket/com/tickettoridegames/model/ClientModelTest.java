package ticket.com.tickettoridegames.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.utility.model.City;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ClientModelTest {
    @Test
    public void testGetPoints() {
        String userId = "id";
        Integer points = 5;

        User user = new User();
        user.setId(userId);
        Player player = new Player(user.getUsername(), user.getId());
        player.setId(userId);
        player.setPoints(points);
        Game game = new Game();
        game.setMaxPlayers(3);
        game.addPlayers(player);

        Map<String, Game> games = new HashMap<>();
        games.put("id", game);

        ClientModel.get_instance().setUser(user);
        ClientModel.get_instance().setGames(games);

        Map<String, Integer> allPoints = ClientModel.get_instance().getPoints();

        assertEquals(allPoints.get(userId), points);
    }

    @Test
    public void testPhase2Simple() {
        ClientModel clientModel = ClientModel.get_instance();
        initGame();

        //mock pick some destination cards
        clientModel.addDestinationCards(
                new DestinationCard(new City("Saltlake"), 5),
                new DestinationCard(new City("Colorado"), 5)
        );
        assertEquals(clientModel.getDestinationCards().size(), 2);

        //wait for turn
        while(!clientModel.isMyTurn()) { clientModel.switchTurn(); }
        do { //and another time around
            clientModel.switchTurn();
        } while(!clientModel.isMyTurn());

        //get my hand at beginning
        List<TrainCard> hand = clientModel.getMyHand();
        assertEquals(hand.size(), 4);

        //claim a route

        //get claimed routes

        //get points

        //get my trainsCount

        //get myHand again

        //get Counts of cards

        //draw a card
        TrainCard card1 = clientModel.getDeckTop();
        TrainCard card2 = clientModel.drawACard();
        TrainCard card3 = clientModel.getDeckTop();
        assertEquals(card1, card2);
        assertNotEquals(card1, card3);
    }

    private void initGame() {
        String userId = "id";
        Integer points = 5;

        User user = new User();
        user.setId(userId);
        Player player = new Player(user.getUsername(), user.getId());
        Player player2 = new Player("player2", "2");
        player.setPoints(points);
        Game game = new Game();
        game.setMaxPlayers(3);
        game.addPlayers(player);
        game.addPlayers(player2);

        Map<String, Game> games = new HashMap<>();
        games.put("id", game);

        ClientModel.get_instance().setUser(user);
        ClientModel.get_instance().setGames(games);

        ClientModel.get_instance().initGame();
    }
}
