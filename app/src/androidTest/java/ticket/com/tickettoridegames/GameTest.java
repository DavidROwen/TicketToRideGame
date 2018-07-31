package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        colors.put("id1", Player.COLOR.RED);
        colors.put("id2", Player.COLOR.YELLOW);
        game.setPlayersColors(colors);
    }

    @Test
    public void testClaimRoute() {
//        initToGamePlay();
//
//        Route routeBlack = new Route("a_b", new City("a"), new City("b"), 2, TrainCard.TRAIN_TYPE.BLACK, 1);
//        Route routeWhite = new Route("b_c", new City("b"), new City("c"), 2, TrainCard.TRAIN_TYPE.WHITE, 1);
//        Route routeWhiteLong = new Route("b_c", new City("b"), new City("c"), 3, TrainCard.TRAIN_TYPE.WHITE, 1);
//
//        //try to claim without the cards
//        assertFalse(game.claimRoute(player1.getId(), routeBlack.NAME)); //no cards
//
//        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
//        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
//        assertFalse(game.claimRoute(player1.getId(), routeBlack.NAME)); //wrong cards
//
//        //claim
//        assertTrue(game.claimRoute(player1.getId(), routeWhite.NAME));
////        assertEquals(game.getMap().getClaimedRoutes().contains()getRoutes().get(player1.getId()).size(), 1); //track claim
//        assertEquals(player1.getTrainCards().size(), 0); //cashed in cards
//
//        //try to reclaim
//        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
//        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
//        assertFalse(game.claimRoute(player1.getId(), routeWhite.NAME));
//
//        //claim with wilds
//        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WILD));
//        assertTrue(game.claimRoute(player1.getId(), routeWhiteLong.NAME));
    }

    @Test
    public void testClaimRouteDoubles() {
        initToGamePlay();
        Map<String, Route> routes = game.getMap().getRoutes();

        //not double
        for(int i = 0; i < 6; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.YELLOW));
        }
        assertTrue(game.claimRoute(player1.getId(), "seattle_helena").isSuccess());

        //other not claimed
        //green/pink 5
        for(int i = 0; i < 5; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.GREEN));
        }
        assertTrue(game.claimRoute(player1.getId(), "portland_sanFran_first").isSuccess());

        //insufficient players
        for(int i = 0; i < 5; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.PINK));
        }
        assertFalse(game.claimRoute(player1.getId(), "portland_sanFran_second").isSuccess());

        //add players
        Player player3 = new Player("username3", "id3");
        game.addPlayers(player3);
        Player player4 = new Player("username4", "id4");
        game.addPlayers(player4);

        //claimed by same player
        assertFalse(game.claimRoute(player1.getId(), "portland_sanFran_second").isSuccess());

        //normal
        for(int i = 0; i < 5; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.PINK));
        }
        assertFalse(game.claimRoute(player2.getId(), "portland_sanFran_second").isSuccess());

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
        game.setMaxPlayers(4);

        player1 = new Player("username1", "id1");
        game.addPlayers(player1);
        player2 = new Player("username2", "id2");
        game.addPlayers(player2);
    }
}
