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
import ticket.com.utility.web.Serializer;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    String gameId = "gameId";
    String playerId = "playerId";

    @Test
    public void testTransferMultiCommand() {
        MultiCommand multi = setupMultiCommand();

        String multiString = Serializer.toJson(multi);
        MultiCommand multi2 = (MultiCommand) Serializer.fromJson(multiString, MultiCommand.class);

        TrainCard card = (TrainCard) multi2.execute();
        assertEquals(card, null);
    }

    private MultiCommand setupMultiCommand() {
        Tester.clear();

        addGameToModel();
        addPlayerToGame();
        addDeckToGame();

//        ServerModel.getGame(gameId);
//        game.drawTrainCard(playerId);
        List<Command> commands = new LinkedList<>();
        commands.add(new Command(ServerModel.class.getName(), null, "getGame", new Object[]{gameId}));
        commands.add(new Command(Game.class.getName(), null, "drawTrainCard", new Object[]{playerId}));

        List<Class<?>> returnTypes = new LinkedList<>();
        returnTypes.add(Game.class);
        returnTypes.add(TrainCard.class);

        return new MultiCommand(commands, returnTypes);
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
