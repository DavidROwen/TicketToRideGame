package tests;

import org.junit.Test;

import java.util.Stack;

import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Command;

import static junit.framework.TestCase.assertTrue;

public class ServerModelTest {
    String gameId = "gameId";
    String playerId = "playerId";

    @Test
    public void testExecOnGame() {
//        addGameToModel();
//        addPlayerToGame();
//        addDeckToGame();
//
//        Command command = new Command(Game.class.getName(), null, "drawTrainCard", new Object[]{playerId});
//
//        boolean successful = ServerModel.execOnGame(gameId, command);
//        assertTrue(successful);
    }

    private void addDeckToGame() {
        Stack deck = new Stack();
        deck.add(new TrainCard(TrainCard.TRAIN_TYPE.WILD));
        ServerModel.getInstance().getGames().get(gameId).setTrainCardsDeck(deck);
    }

    private void addPlayerToGame() {
        Player player = new Player("player", playerId);
        ServerModel.getInstance().getGames().get(gameId).addPlayers(player);
    }

    private void addGameToModel() {
        Game game = new Game();
        game.setId(gameId);

        ServerModel.getInstance().getGames().put(gameId, game);
    }
}
