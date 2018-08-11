package tests;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import ticket.com.server.server.MultiCommand;
import ticket.com.server.server.model.ServerModel;
import ticket.com.server.server.service.Tester;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Command;

import static org.junit.Assert.*;

public class MultiCommandTest {
    String gameId = "gameId";
    String playerId = "playerId";

    @Test
    public void execute() {
        Tester.clear();

        addGameToModel();
        addPlayerToGame();
        addDeckToGame();

//        Game game = ServerModel.getInstance().getGames().get(gameId);
//        game.drawTrainCard(playerId);
        List<Command> commands = new LinkedList<>();
        commands.add(new Command(ServerModel.class.getName(), null, "getInstance", null));
        commands.add(new Command(ServerModel.class.getName(), null, "getGames", null));
        commands.add(new Command(HashMap.class.getName(), null, null,"get", new Class<?>[]{Object.class}, new Object[]{gameId}));
        commands.add(new Command(Game.class.getName(), null, "drawTrainCard", new Object[]{playerId}));

        List<Class<?>> returnTypes = new LinkedList<>();
        returnTypes.add(ServerModel.class);
        returnTypes.add(HashMap.class);
        returnTypes.add(Game.class);
        returnTypes.add(TrainCard.class);

        MultiCommand multi = new MultiCommand(commands, returnTypes);
        TrainCard card = (TrainCard) multi.execute();
        assertEquals(card, null);
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