package ticket.com.tickettoridegames.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import ticket.com.tickettoridegames.utility.model.City;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    @Test
    public void testSetColors() {
        Game game = new Game();
        game.addPlayers(new Player("username", "id"));
        Map<String, Player.COLOR> colors = new HashMap<>();
        colors.put("id", Player.COLOR.RED);
        game.setPlayersColors(colors);
    }

    @Test
    public void testClaimRoute() {
        Game game = new Game();
        game.setMaxPlayers(2);
        Player player = new Player("username", "id");
        game.addPlayers(player);

        Route routeBlack = new Route(new City("a"), new City("b"), 2, TrainCard.TRAIN_TYPE.BLACK);
        Route routeWhite = new Route(new City("b"), new City("c"), 2, TrainCard.TRAIN_TYPE.WHITE);
        Route routeWhiteLong = new Route(new City("b"), new City("c"), 3, TrainCard.TRAIN_TYPE.WHITE);

        //try to claim without the cards
        assertFalse(game.claimRoute(player.getId(), routeBlack)); //no cards

        player.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        player.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        assertFalse(game.claimRoute(player.getId(), routeBlack)); //wrong cards

        //claim
        assertTrue(game.claimRoute(player.getId(), routeWhite));
        assertEquals(game.getRoutes().get(player.getId()).size(), 1); //track claim
        assertEquals(player.getTrainCards().size(), 0); //cashed in cards

        //try to reclaim
        player.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        player.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        assertFalse(game.claimRoute(player.getId(), routeWhite));

        //claim with wilds
        player.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WILD));
        assertTrue(game.claimRoute(player.getId(), routeWhiteLong));
    }
}
