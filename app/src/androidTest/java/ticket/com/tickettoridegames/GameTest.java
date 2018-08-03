package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ticket.com.utility.model.Game;
import ticket.com.utility.model.Pair;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.PlayerStats;
import ticket.com.utility.model.Route;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    Game game;
    Player player1;
    Player player2;

    @Test
    public void testInitGame() {
        initToGamePlay();
    }

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
        initToGamePlay();
        Map<String, Route> routes = game.getMap().getRoutes();

        Route winnipeg_duluth = routes.get("winnipeg_duluth"); //4 blacks
        assertFalse(game.claimRoute(player1.getId(), winnipeg_duluth.NAME).isSuccess()); //no cards

        for(int i = 0; i < 4; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        }
        assertFalse(game.claimRoute(player1.getId(), winnipeg_duluth.NAME).isSuccess()); //wrong cards

        Route littleRock_Nashville = routes.get("littleRock_nashville"); //3 whites
        assertTrue(game.claimRoute(player1.getId(), littleRock_Nashville.NAME).isSuccess()); //claim
        assertFalse(routes.get("littleRock_nashville").canClaim().isSuccess()); //track claimed
        assertEquals(4+1, player1.getTrainCards().size()); //cashed in cards

        for(int i = 0; i < 2; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WHITE));
        }
        assertFalse(game.claimRoute(player1.getId(), littleRock_Nashville.NAME).isSuccess()); //fail to reclaim


        Route chicago_toronto = routes.get("chicago_toronto"); //4 whites
        player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.WILD));
        assertTrue(game.claimRoute(player1.getId(), chicago_toronto.NAME).isSuccess()); //claim with wilds
    }

    @Test
    public void testClaimRouteDoubles() {
        initToGamePlay();
        Map<String, Route> routes = game.getMap().getRoutes();

        for(int i = 0; i < 6; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.YELLOW));
        }

        Result result = game.claimRoute(player1.getId(), "seattle_helena");
        assertTrue(result.isSuccess()); //not double
        System.out.println("Success: " + result.getErrorMessage());

        //green/pink 5
        for(int i = 0; i < 5; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.GREEN));
        }
        result = game.claimRoute(player1.getId(), "portland_sanFran_first");
        assertTrue(result.isSuccess()); //other not claimed
        System.out.println("Success: " + result.getErrorMessage());

        for(int i = 0; i < 5; i++) {
            player2.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.PINK));
        }
        result = game.claimRoute(player2.getId(), "portland_sanFran_second");
        assertFalse(result.isSuccess()); //insufficient players
        System.out.println("Insufficient: " + result.getErrorMessage());

        //add sufficient players
        Player player3 = new Player("username3", "id3");
        game.addPlayers(player3);
        Player player4 = new Player("username4", "id4");
        game.addPlayers(player4);

        for(int i = 0; i < 5; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.PINK));
        }
        result = game.claimRoute(player1.getId(), "portland_sanFran_second");
        assertFalse(result.isSuccess());
        System.out.println("Same player: " + result.getErrorMessage()); //claimed by same player

        result = game.claimRoute(player2.getId(), "portland_sanFran_second");
        assertTrue(result.isSuccess());
        System.out.println("Success: " + result.getErrorMessage()); //normal
    }

    @Test
    public void testClaimGrey() {
        initToGamePlay();

        for (int i = 0; i < 2; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.BLUE));
        }
        Result falseResult = game.claimRoute(player1.getId(), "elPaso_santaFe"); //2 wilds
        assertFalse(falseResult.isSuccess()); //wrong function
        Result trueResult = game.claimRoute(player1.getId(), "elPaso_santaFe", TrainCard.TRAIN_TYPE.BLUE); //2 wilds
        assertTrue(trueResult.isSuccess()); //normal
    }

    @Test
    public void testClaimRandom() {
        initToGamePlay();

        for (int i = 0; i < 2; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.BLUE));
        }
        Result result = game.claimRoute(player1.getId(), "KC_saintLouis_first"); //2 blue
        assertTrue(result.isSuccess()); //normal
        System.out.println("Success: " + result.getErrorMessage());
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

    @Test
    public void testGetRoutes() {
        initToGamePlay();

        //get ready to claim routes
        for(int i = 0; i < 2; i++) {
            player1.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.GREEN));
        }
        for(int i = 0; i < 2; i++) {
            player2.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.YELLOW));
        }

        //claim some routes
        game.claimRoute(player1.getId(),"pittsburg_newYork_first"); //2 green
        game.claimRoute(player2.getId(),"newYork_boston_first"); //2 yellows

        List<Pair<String, Integer>> routes = game.getClaimedRoutes();
        //check that they're in the returned list
        assertTrue(routes.size() == 2);
        assertTrue(routes.get(0).first.equals("pittsburg_newYork_first")
                || routes.get(0).first.equals("newYork_boston_first")
        );
        assertTrue(routes.get(1).first.equals("pittsburg_newYork_first")
                || routes.get(1).first.equals("newYork_boston_first")
        );
    }

    public void initToGamePlay() {
        game = new Game();
        game.setMaxPlayers(4);

        player1 = new Player("username1", "id1");
        game.addPlayers(player1);
        player2 = new Player("username2", "id2");
        game.addPlayers(player2);

        game.setId("id");
        game.initGame();
        game.initGameNonRandom();
    }
}
