package ticket.com.tickettoridegames.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.utility.model.City;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.PlayerStats;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    Game game;
    Player player1;
    Player player2;

    @Test //with enum
    public void testSetColors() {
        initToGamePlay();

        Map<String, Player.COLOR> colors = new HashMap<>();
        colors.put("id", Player.COLOR.RED);
        game.setPlayersColors(colors);
    }

    @Test
    public void testClaimRoute() {
        initToGamePlay();

        Route routeBlack = new Route(new City("a"), new City("b"), 2, TrainCard.TRAIN_TYPE.BLACK);
        Route routeWhite = new Route(new City("b"), new City("c"), 2, TrainCard.TRAIN_TYPE.WHITE);
        Route routeWhiteLong = new Route(new City("b"), new City("c"), 3, TrainCard.TRAIN_TYPE.WHITE);

        //try to claim without the cards
        assertFalse(game.claimRoute(player1.getId(), routeBlack)); //no cards

        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        assertFalse(game.claimRoute(player1.getId(), routeBlack)); //wrong cards

        //claim
        assertTrue(game.claimRoute(player1.getId(), routeWhite));
        assertEquals(game.getRoutes().get(player1.getId()).size(), 1); //track claim
        assertEquals(player1.getTrainCards().size(), 0); //cashed in cards

        //try to reclaim
        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        assertFalse(game.claimRoute(player1.getId(), routeWhite));

        //claim with wilds
        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WILD));
        assertTrue(game.claimRoute(player1.getId(), routeWhiteLong));
    }

    @Test
    public void testUpdateStats() {
        initToGamePlay();

        game.initGame();
        game.initGameNonRandom();

        game.drawTrainCard(player1.getId());
        game.drawTrainCard(player1.getId());
        game.drawTrainCard(player2.getId());

        List<PlayerStats> stats = game.getPlayerStats();
        assertEquals(stats.size(), 2);
        assertEquals(stats.get(0).getNumberOfCards(), (Integer)5); //player2
        assertEquals(stats.get(1).getNumberOfCards(), (Integer)6); //player1
    }

    public void initToGamePlay() {
        game = new Game();
        game.setMaxPlayers(2);

        player1 = new Player("username1", "id1");
        game.addPlayers(player1);
        player2 = new Player("username2", "id2");
        game.addPlayers(player2);
    }
}
